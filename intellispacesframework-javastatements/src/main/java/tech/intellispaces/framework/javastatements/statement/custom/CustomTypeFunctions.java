package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.ContextTypeParameter;
import tech.intellispaces.framework.javastatements.context.NameContextFunctions;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.method.MethodParam;
import tech.intellispaces.framework.javastatements.statement.method.MethodParams;
import tech.intellispaces.framework.javastatements.statement.method.MethodSignature;
import tech.intellispaces.framework.javastatements.statement.method.MethodSignatures;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatements;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.ExceptionCompatibleType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NonPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;
import tech.intellispaces.framework.javastatements.statement.type.TypeFunctions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Custom type related functions.
 */
public interface CustomTypeFunctions {

  static Optional<CustomType> getExtendedClass(CustomStatement statement) {
    return statement.parentTypes().stream()
        .filter(ref -> StatementTypes.Class.equals(ref.targetType().statementType()))
        .reduce((ref1, ref2) -> {
          throw JavaStatementException.withMessage("Multiple extended classes: {}, {}", ref1, ref2);
        });
  }

  static List<CustomType> getImplementedInterfaces(CustomStatement statement) {
    return statement.parentTypes().stream()
        .filter(t -> StatementTypes.Interface.equals(t.targetType().statementType()))
        .toList();
  }

  static List<MethodStatement> getActualMethods(CustomStatement customStatement, TypeContext typeContext, Session session) {
    List<MethodStatement> actualMethods = new ArrayList<>();
    customStatement.declaredMethods().stream()
        .map(method -> getEffectiveMethod(method, typeContext))
        .forEach(actualMethods::add);
    inheritedMethods(customStatement, actualMethods, typeContext);
    return actualMethods;
  }

  private static MethodStatement getEffectiveMethod(MethodStatement originMethod, TypeContext typeContext) {
    return MethodStatements.build(
        originMethod.owner(),
        getEffectiveMethodSignature(originMethod.signature(), typeContext)
    );
  }

  private static MethodSignature getEffectiveMethodSignature(
      MethodSignature originMethodSignature, TypeContext typeContext
  ) {
    return MethodSignatures.build()
        .isAbstract(originMethodSignature.isAbstract())
        .isPublic(originMethodSignature.isPublic())
        .isDefault(originMethodSignature.isDefault())
        .isStatic(originMethodSignature.isStatic())
        .name(originMethodSignature.name())
        .annotations(originMethodSignature.annotations())
        .returnType(originMethodSignature.returnType().isPresent()
            ? getActualTypeReference(originMethodSignature.returnType().orElseThrow(), typeContext) : null
        )
        .defaultValue(originMethodSignature.defaultValue().orElse(null))
        .typeParameters(
            originMethodSignature.typeParameters().stream()
                .map(e -> (NamedType) getActualTypeReference(e, typeContext))
                .toList()
        )
        .params(originMethodSignature.params().stream()
            .map(p -> MethodParams.build()
                .name(p.name())
                .type(getActualTypeReference(p.type(), typeContext))
                .get())
            .toList()
        )
        .exceptions(originMethodSignature.exceptions().stream()
            .map(e -> (ExceptionCompatibleType) getActualTypeReference(e, typeContext))
            .toList()
        )
        .get();
  }

  private static void inheritedMethods(
      CustomStatement customStatement, List<MethodStatement> allMethods, TypeContext typeContext
  ) {
    customStatement.parentTypes().forEach(parent ->
        extractMethods(parent, allMethods, typeContext));
  }

  private static void extractMethods(
      CustomType customTypeReference, List<MethodStatement> allMethods, TypeContext typeContext
  ) {
    CustomStatement customStatement = customTypeReference.targetType();
    TypeContext actualNameContext = NameContextFunctions.getActualNameContext(
        typeContext, customStatement.typeParameters(), customTypeReference.typeArguments()
    );
    customStatement.declaredMethods().forEach(
        method -> addMethod(method, allMethods, actualNameContext)
    );
    inheritedMethods(customStatement, allMethods, actualNameContext);
  }

  private static void addMethod(
      MethodStatement addedMethod, List<MethodStatement> allMethods, TypeContext typeContext
  ) {
    MethodStatement effectiveAddedMethod = getEffectiveMethod(addedMethod, typeContext);
    MethodSignature effectiveAddedSignature = effectiveAddedMethod.signature();
    int index = 0;
    for (MethodStatement method : allMethods) {
      MethodSignature methodSignature = method.signature();
      if (effectiveAddedSignature.name().equals(methodSignature.name())) {
        if (isSameParams(effectiveAddedSignature, methodSignature)) {
          if (effectiveAddedSignature.returnType().isEmpty() && methodSignature.returnType().isEmpty()) {
            // Ignore override method
            return;
          }
          Type methodReturnType = methodSignature.returnType().get();
          Type effectiveAddedMethodReturnType = effectiveAddedSignature.returnType().get();
          Optional<Type> narrowType = TypeFunctions.narrowestOf(
              methodReturnType, effectiveAddedMethodReturnType
          );
          if (narrowType.isEmpty()) {
            throw JavaStatementException.withMessage("Incompatible types: {} and {} of method {}",
                methodReturnType, effectiveAddedMethodReturnType, methodSignature.name());
          }
          if (narrowType.get() == effectiveAddedMethodReturnType
              && AnnotationFunctions.hasAnnotation(effectiveAddedMethod.signature(), Override.class)
          ) {
            // Replace override method
            allMethods.set(index, effectiveAddedMethod);
          }
          // Ignore override method
          return;
        }
      }
      index++;
    }
    allMethods.add(effectiveAddedMethod);
  }

  private static Type getActualTypeReference(Type type, TypeContext typeContext) {
    if (type.asNamedType().isPresent())  {
      NamedType namedTypeReference = type.asNamedType().orElseThrow();
      Optional<NonPrimitiveType> actualType = typeContext
          .get(namedTypeReference.name())
          .map(ContextTypeParameter::actualType);
      if (actualType.isPresent()) {
        return actualType.get();
      }
    }
    return type;
  }

  private static boolean isSameParams(MethodSignature addedMethod, MethodSignature otherMethod) {
    boolean sameParams = true;
    Iterator<MethodParam> testMethodParams = addedMethod.params().iterator();
    Iterator<MethodParam> methodParams = otherMethod.params().iterator();
    while (testMethodParams.hasNext() && methodParams.hasNext()) {
      if (!TypeFunctions.isEqualTypes(testMethodParams.next().type(), methodParams.next().type())) {
        sameParams = false;
        break;
      }
    }
    if (testMethodParams.hasNext() || methodParams.hasNext()) {
      sameParams = false;
    }
    return sameParams;
  }

  static List<CustomStatement> allParents(CustomStatement customStatement) {
    List<CustomStatement> curParents = customStatement.parentTypes().stream()
        .map(CustomType::targetType)
        .toList();
    List<CustomStatement> parents = new ArrayList<>(curParents);
    curParents.forEach(p -> populateParents(p, parents));
    return parents;
  }

  private static void populateParents(CustomStatement customStatement, List<CustomStatement> parents) {
    var curParents = customStatement.parentTypes().stream()
        .map(CustomType::targetType)
        .toList();
    parents.addAll(curParents);
    curParents.forEach(p -> populateParents(p, parents));
  }

  static boolean hasParent(CustomStatement customStatement, String parentCanonicalName) {
    for (CustomType parent : customStatement.parentTypes()) {
      if (parentCanonicalName.equals(parent.targetType().canonicalName())) {
        return true;
      }
    }
    for (CustomType parent : customStatement.parentTypes()) {
      if (parent.targetType().hasParent(parentCanonicalName)) {
        return true;
      }
    }
    return false;
  }

  static String getTypeParametersDeclaration(CustomStatement customStatement, boolean fullDeclaration) {
    var parametersSource = customStatement.typeParameters().stream()
        .map(param -> TypeFunctions.getNamedTypeReferenceDeclaration(param, fullDeclaration))
        .collect(Collectors.joining(", "));
    return (parametersSource.isEmpty() ? "" : "<" + parametersSource + ">");
  }
}
