package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.ContextTypeParameter;
import tech.intellispacesframework.javastatements.context.NameContextFunctions;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.exception.JavaStatementException;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NonPrimitiveTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;
import tech.intellispacesframework.javastatements.statement.reference.TypeReferenceFunctions;

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
        .filter(ref -> StatementTypes.Class.equals(ref.targetType().statementType()))
        .reduce((ref1, ref2) -> {
          throw JavaStatementException.withMessage("Multiple extended classes: {}, {}", ref1, ref2);
        });
  }

  static List<CustomTypeReference> getImplementedInterfaces(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(t -> StatementTypes.Interface.equals(t.targetType().statementType()))
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
    return MethodStatementBuilder.build(
        originMethod.holder(),
        getEffectiveMethodSignature(originMethod.signature(), typeContext)
    );
  }

  private static MethodSignature getEffectiveMethodSignature(
      MethodSignature originMethodSignature, TypeContext typeContext
  ) {
    return MethodSignatureBuilder.get()
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
                .map(e -> (NamedTypeReference) getActualTypeReference(e, typeContext))
                .toList()
        )
        .params(originMethodSignature.params().stream()
            .map(p -> MethodParamBuilder.get()
                .name(p.name())
                .type(getActualTypeReference(p.type(), typeContext))
                .build())
            .toList()
        )
        .exceptions(originMethodSignature.exceptions().stream()
            .map(e -> (ExceptionCompatibleTypeReference) getActualTypeReference(e, typeContext))
            .toList()
        )
        .build();
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
    CustomType customType = customTypeReference.targetType();
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
          TypeReference methodReturnType = methodSignature.returnType().get();
          TypeReference effectiveAddedMethodReturnType = effectiveAddedSignature.returnType().get();
          Optional<TypeReference> narrowType = TypeReferenceFunctions.narrowestOf(methodReturnType, effectiveAddedMethodReturnType);
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

  private static TypeReference getActualTypeReference(TypeReference typeReference, TypeContext typeContext) {
    if (typeReference.asNamedTypeReference().isPresent())  {
      NamedTypeReference namedTypeReference = typeReference.asNamedTypeReference().orElseThrow();
      Optional<NonPrimitiveTypeReference> actualType = typeContext
          .get(namedTypeReference.name())
          .map(ContextTypeParameter::type);
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
      if (!TypeReferenceFunctions.compareStrict(testMethodParams.next().type(), methodParams.next().type())) {
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
        .map(CustomTypeReference::targetType)
        .toList();
    List<CustomType> parents = new ArrayList<>(curParents);
    curParents.forEach(p -> populateParents(p, parents));
    return parents;
  }

  private static void populateParents(CustomType customType, List<CustomType> parents) {
    var curParents = customType.parentTypes().stream()
        .map(CustomTypeReference::targetType)
        .collect(Collectors.toList());
    parents.addAll(curParents);
    curParents.forEach(p -> populateParents(p, parents));
  }

  static String getTypeParametersDeclaration(CustomType customType, boolean fullDeclaration) {
    var parametersSource = customType.typeParameters().stream()
        .map(param -> TypeReferenceFunctions.getNamedTypeReferenceDeclaration(param, fullDeclaration))
        .collect(Collectors.joining(", "));
    return (parametersSource.isEmpty() ? "" : "<" + parametersSource + ">");
  }
}
