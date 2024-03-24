package intellispaces.javastatements.function;

import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.StatementTypes;
import intellispaces.javastatements.object.custom.MethodParamBuilder;
import intellispaces.javastatements.object.custom.MethodSignatureBuilder;
import intellispaces.javastatements.object.custom.MethodStatementBuilder;

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
          throw new JavaStatementException("Multiple extended classes: {}, {}", ref1, ref2);
        });
  }

  static List<CustomTypeReference> getImplementedInterfaces(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(t -> StatementTypes.Interface.equals(t.targetType().statementType()))
        .toList();
  }

  static List<MethodStatement> getActualMethods(CustomType customType, NameContext nameContext, Session session) {
    List<MethodStatement> actualMethods = new ArrayList<>();
    customType.declaredMethods().stream()
        .map(method -> getEffectiveMethod(method, nameContext))
        .forEach(actualMethods::add);
    inheritedMethods(customType, actualMethods, nameContext);
    return actualMethods;
  }

  private static MethodStatement getEffectiveMethod(MethodStatement originMethod, NameContext nameContext) {
    return MethodStatementBuilder.build(
        originMethod.holder(),
        getEffectiveMethodSignature(originMethod.signature(), nameContext)
    );
  }

  private static MethodSignature getEffectiveMethodSignature(MethodSignature originMethodSignature, NameContext nameContext) {
    return MethodSignatureBuilder.get()
        .isDefault(originMethodSignature.isDefault())
        .isStatic(originMethodSignature.isStatic())
        .name(originMethodSignature.name())
        .annotations(originMethodSignature.annotations())
        .returnType(originMethodSignature.returnType().isPresent()
            ? getActualTypeReference(originMethodSignature.returnType().orElseThrow(), nameContext) : null
        )
        .defaultValue(originMethodSignature.defaultValue().orElse(null))
        .typeParameters(
            originMethodSignature.typeParameters().stream()
                .map(e -> (NamedTypeReference) getActualTypeReference(e, nameContext))
                .toList()
        )
        .params(originMethodSignature.params().stream()
            .map(p -> MethodParamBuilder.get()
                .name(p.name())
                .type(getActualTypeReference(p.type(), nameContext))
                .build())
            .toList()
        )
        .exceptions(originMethodSignature.exceptions().stream()
            .map(e -> (ExceptionCompatibleTypeReference) getActualTypeReference(e, nameContext))
            .toList()
        )
        .build();
  }

  private static void inheritedMethods(CustomType customType, List<MethodStatement> allMethods, NameContext nameContext) {
    customType.parentTypes().forEach(parent ->
        extractMethods(parent, allMethods, nameContext));
  }

  private static void extractMethods(CustomTypeReference customTypeReference, List<MethodStatement> allMethods, NameContext nameContext) {
    CustomType customType = customTypeReference.targetType();
    NameContext actualNameContext = NameContextFunctions.getActualNameContext(
        nameContext, customType.typeParameters(), customTypeReference.typeArguments()
    );
    customType.declaredMethods().forEach(
        method -> addMethod(method, allMethods, actualNameContext)
    );
    inheritedMethods(customType, allMethods, actualNameContext);
  }

  private static void addMethod(MethodStatement addedMethod, List<MethodStatement> allMethods, NameContext nameContext) {
    MethodStatement effectiveAddedMethod = getEffectiveMethod(addedMethod, nameContext);
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
            throw new JavaStatementException("Incompatible types: {} and {} of method {}", methodReturnType, effectiveAddedMethodReturnType,
                methodSignature.name());
          }
          if (narrowType.get() == effectiveAddedMethodReturnType && AnnotationFunctions.hasAnnotation(effectiveAddedMethod.signature(), Override.class)) {
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

  private static TypeReference getActualTypeReference(TypeReference typeReference, NameContext nameContext) {
    if (typeReference.asNamedTypeReference().isPresent())  {
      NamedTypeReference namedTypeReference = typeReference.asNamedTypeReference().orElseThrow();
      Optional<NonPrimitiveTypeReference> actualType = nameContext.get(namedTypeReference.name()).map(ContextTypeParameter::actualType);
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
}
