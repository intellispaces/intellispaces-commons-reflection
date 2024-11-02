package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.context.ContextTypeParameter;
import intellispaces.common.javastatement.context.NameContextFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.exception.JavaStatementException;
import intellispaces.common.javastatement.method.MethodParam;
import intellispaces.common.javastatement.method.MethodParams;
import intellispaces.common.javastatement.method.MethodSignature;
import intellispaces.common.javastatement.method.MethodSignatures;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.method.Methods;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.CustomTypeReferences;
import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.reference.ThrowableReference;
import intellispaces.common.javastatement.reference.TypeReference;
import intellispaces.common.javastatement.reference.TypeReferenceFunctions;
import intellispaces.common.javastatement.session.Session;

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
          throw JavaStatementException.withMessage("Multiple extended classes: {0}, {1}", ref1, ref2);
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
    addInheritedMethods(customType, actualMethods, typeContext);
    return actualMethods;
  }

  private static MethodStatement getEffectiveMethod(MethodStatement originMethod, TypeContext typeContext) {
    return Methods.get(
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
            .map(e -> (ThrowableReference) getActualTypeReference(e, typeContext))
            .toList()
        )
        .get();
  }

  private static void addInheritedMethods(
      CustomType customType, List<MethodStatement> actualMethods, TypeContext typeContext
  ) {
    customType.parentTypes().forEach(parent -> addParentMethods(parent, actualMethods, typeContext));
  }

  private static void addParentMethods(
      CustomTypeReference parentReference, List<MethodStatement> actualMethods, TypeContext typeContext
  ) {
    CustomType parentType = parentReference.targetType();
    TypeContext actualNameContext = NameContextFunctions.getActualNameContext(
        typeContext, parentType.typeParameters(), parentReference.typeArguments()
    );
    parentType.declaredMethods().forEach(m -> addMethod(m, actualMethods, actualNameContext));
    addInheritedMethods(parentType, actualMethods, actualNameContext);
  }

  private static void addMethod(
      MethodStatement addedMethod, List<MethodStatement> actualMethods, TypeContext typeContext
  ) {
    MethodStatement effectiveAddedMethod = getEffectiveMethod(addedMethod, typeContext);
    MethodSignature effectiveAddedSignature = effectiveAddedMethod.signature();
    for (MethodStatement method : actualMethods) {
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
            throw JavaStatementException.withMessage("Incompatible types: {0} and {1} of method {2}",
                methodReturnTypeReference, effectiveAddedMethodReturnTypeReference, methodSignature.name());
          }
          return;
        }
      }
    }
    actualMethods.add(effectiveAddedMethod);
  }

  private static TypeReference getActualTypeReference(TypeReference typeReference, TypeContext typeContext) {
    if (typeReference.isNamedReference())  {
      NamedReference namedReference = typeReference.asNamedReferenceOrElseThrow();
      Optional<NotPrimitiveReference> actualType = typeContext
          .get(namedReference.name())
          .map(ContextTypeParameter::actualType);
      if (actualType.isPresent()) {
        return actualType.get();
      }
    } else if (typeReference.isCustomTypeReference())  {
      CustomTypeReference customTypeReference = typeReference.asCustomTypeReferenceOrElseThrow();
      CustomType baseType = customTypeReference.targetType();
      List<NotPrimitiveReference> actualTypeArguments = customTypeReference.typeArguments().stream()
          .map(arg -> (NotPrimitiveReference) getActualTypeReference(arg, typeContext))
          .toList();
      return CustomTypeReferences.get(baseType, actualTypeArguments);
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
        .map(CustomTypeReference::targetType)
        .toList();
    List<CustomType> parents = new ArrayList<>(curParents);
    curParents.forEach(p -> populateParents(p, parents));
    return parents;
  }

  private static void populateParents(CustomType customType, List<CustomType> parents) {
    var curParents = customType.parentTypes().stream()
        .map(CustomTypeReference::targetType)
        .toList();
    parents.addAll(curParents);
    curParents.forEach(p -> populateParents(p, parents));
  }

  static boolean hasParent(CustomType customType, String parentCanonicalName) {
    for (CustomTypeReference parent : customType.parentTypes()) {
      if (parentCanonicalName.equals(parent.targetType().canonicalName())) {
        return true;
      }
    }
    for (CustomTypeReference parent : customType.parentTypes()) {
      if (parent.targetType().hasParent(parentCanonicalName)) {
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
