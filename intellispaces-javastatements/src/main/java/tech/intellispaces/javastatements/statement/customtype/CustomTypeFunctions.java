package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.javastatements.context.ContextTypeParameter;
import tech.intellispaces.javastatements.context.NameContextFunctions;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.exception.JavaStatementException;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.method.MethodParam;
import tech.intellispaces.javastatements.statement.method.MethodParams;
import tech.intellispaces.javastatements.statement.method.MethodSignature;
import tech.intellispaces.javastatements.statement.method.MethodSignatures;
import tech.intellispaces.javastatements.statement.method.MethodStatement;
import tech.intellispaces.javastatements.statement.method.MethodStatements;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.statement.reference.ThrowableTypeReference;
import tech.intellispaces.javastatements.statement.reference.TypeReferenceFunctions;
import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Custom type related functions.
 */
public interface CustomTypeFunctions {

  static Optional<CustomTypeReference> getExtendedClass(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(ref -> StatementTypes.Class.equals(ref.customType().statementType()))
        .reduce((ref1, ref2) -> {
          throw JavaStatementException.withMessage("Multiple extended classes: {}, {}", ref1, ref2);
        });
  }

  static List<CustomTypeReference> getImplementedInterfaces(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(t -> StatementTypes.Interface.equals(t.customType().statementType()))
        .toList();
  }

  static List<MethodStatement> getActualMethods(CustomType customType, TypeContext typeContext, Session session) {
    List<MethodStatement> actualMethods = new ArrayList<>();
    customType.declaredMethods().stream()
        .map(method -> getEffectiveMethod(method, typeContext))
        .forEach(actualMethods::add);
    inheritedMethods(customType, actualMethods, typeContext);
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
                .map(e -> (NamedReference) getActualTypeReference(e, typeContext))
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
            .map(e -> (ThrowableTypeReference) getActualTypeReference(e, typeContext))
            .toList()
        )
        .get();
  }

  private static void inheritedMethods(
      CustomType customType, List<MethodStatement> allMethods, TypeContext typeContext
  ) {
    customType.parentTypes().forEach(parent ->
        extractMethods(parent, allMethods, typeContext));
  }

  private static void extractMethods(
      CustomTypeReference customTypeReference, List<MethodStatement> allMethods, TypeContext typeContext
  ) {
    CustomType customType = customTypeReference.customType();
    TypeContext actualNameContext = NameContextFunctions.getActualNameContext(
        typeContext, customType.typeParameters(), customTypeReference.typeArguments()
    );
    customType.declaredMethods().forEach(
        method -> addMethod(method, allMethods, actualNameContext)
    );
    inheritedMethods(customType, allMethods, actualNameContext);
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
          TypeReference methodReturnTypeReference = methodSignature.returnType().get();
          TypeReference effectiveAddedMethodReturnTypeReference = effectiveAddedSignature.returnType().get();
          Optional<TypeReference> narrowType = TypeReferenceFunctions.narrowestOf(
              methodReturnTypeReference, effectiveAddedMethodReturnTypeReference
          );
          if (narrowType.isEmpty()) {
            throw JavaStatementException.withMessage("Incompatible types: {} and {} of method {}",
                methodReturnTypeReference, effectiveAddedMethodReturnTypeReference, methodSignature.name());
          }
          if (narrowType.get() == effectiveAddedMethodReturnTypeReference
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

  private static TypeReference getActualTypeReference(TypeReference typeReference, TypeContext typeContext) {
    if (typeReference.asNamed().isPresent())  {
      NamedReference namedReference = typeReference.asNamed().orElseThrow();
      Optional<NotPrimitiveTypeReference> actualType = typeContext
          .get(namedReference.name())
          .map(ContextTypeParameter::actualType);
      if (actualType.isPresent()) {
        return actualType.get();
      }
    }
    return typeReference;
  }

  private static boolean isSameParams(MethodSignature addedMethod, MethodSignature otherMethod) {
    boolean sameParams = true;
    Iterator<MethodParam> testMethodParams = addedMethod.params().iterator();
    Iterator<MethodParam> methodParams = otherMethod.params().iterator();
    while (testMethodParams.hasNext() && methodParams.hasNext()) {
      if (!TypeReferenceFunctions.isEqualTypes(testMethodParams.next().type(), methodParams.next().type())) {
        sameParams = false;
        break;
      }
    }
    if (testMethodParams.hasNext() || methodParams.hasNext()) {
      sameParams = false;
    }
    return sameParams;
  }

  static List<CustomType> allParents(CustomType customType) {
    List<CustomType> curParents = customType.parentTypes().stream()
        .map(CustomTypeReference::customType)
        .toList();
    List<CustomType> parents = new ArrayList<>(curParents);
    curParents.forEach(p -> populateParents(p, parents));
    return parents;
  }

  private static void populateParents(CustomType customType, List<CustomType> parents) {
    var curParents = customType.parentTypes().stream()
        .map(CustomTypeReference::customType)
        .toList();
    parents.addAll(curParents);
    curParents.forEach(p -> populateParents(p, parents));
  }

  static boolean hasParent(CustomType customType, String parentCanonicalName) {
    for (CustomTypeReference parent : customType.parentTypes()) {
      if (parentCanonicalName.equals(parent.customType().canonicalName())) {
        return true;
      }
    }
    for (CustomTypeReference parent : customType.parentTypes()) {
      if (parent.customType().hasParent(parentCanonicalName)) {
        return true;
      }
    }
    return false;
  }

  static String getTypeParametersDeclaration(CustomType customType, boolean fullDeclaration) {
    var parametersSource = customType.typeParameters().stream()
        .map(param -> TypeReferenceFunctions.getNamedTypeReferenceDeclaration(param, fullDeclaration))
        .collect(Collectors.joining(", "));
    return (parametersSource.isEmpty() ? "" : "<" + parametersSource + ">");
  }
}
