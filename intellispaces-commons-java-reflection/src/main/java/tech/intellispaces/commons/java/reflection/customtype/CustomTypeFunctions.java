package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.context.ContextTypeParameter;
import tech.intellispaces.commons.java.reflection.context.NameContextFunctions;
import tech.intellispaces.commons.java.reflection.context.TypeContext;
import tech.intellispaces.commons.java.reflection.exception.JavaStatementExceptions;
import tech.intellispaces.commons.java.reflection.method.MethodParam;
import tech.intellispaces.commons.java.reflection.method.MethodParams;
import tech.intellispaces.commons.java.reflection.method.MethodSignature;
import tech.intellispaces.commons.java.reflection.method.MethodSignatures;
import tech.intellispaces.commons.java.reflection.method.MethodStatement;
import tech.intellispaces.commons.java.reflection.method.Methods;
import tech.intellispaces.commons.java.reflection.reference.CustomTypeReference;
import tech.intellispaces.commons.java.reflection.reference.CustomTypeReferences;
import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.java.reflection.reference.ThrowableReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReferenceFunctions;
import tech.intellispaces.commons.java.reflection.session.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Custom type related functions.
 */
public interface CustomTypeFunctions {

  static Optional<CustomTypeReference> getExtendedClass(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(ref -> StatementTypes.Class.equals(ref.targetType().statementType()))
        .reduce((ref1, ref2) -> {
          throw JavaStatementExceptions.withMessage("Multiple extended classes: {0}, {1}", ref1, ref2);
        });
  }

  static List<CustomTypeReference> getImplementedInterfaces(CustomType statement) {
    return statement.parentTypes().stream()
        .filter(t -> StatementTypes.Interface.equals(t.targetType().statementType()))
        .toList();
  }

  static List<MethodStatement> getActualMethods(CustomType customType, TypeContext typeContext, Session session) {
    try {
      List<MethodStatement> actualMethods = new ArrayList<>();
      customType.declaredMethods().stream()
          .map(method -> getEffectiveMethod(method, typeContext))
          .forEach(actualMethods::add);
      addInheritedMethods(customType, actualMethods, typeContext);
      return actualMethods;
    } catch (Exception e) {
      throw JavaStatementExceptions.withCauseAndMessage(e, "Unable to get actual methods of type {0}",
          customType.canonicalName());
    }
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
                .annotations(p.annotations())
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
    customType.parentTypes().forEach(parent -> addInheritedMethods(parent, actualMethods, typeContext));
  }

  private static void addInheritedMethods(
      CustomTypeReference parentReference, List<MethodStatement> actualMethods, TypeContext typeContext
  ) {
    CustomType parentType = parentReference.targetType();
    TypeContext actualNameContext = NameContextFunctions.getActualNameContext(
        typeContext, parentType.typeParameters(), parentReference.typeArguments()
    );
    parentType.declaredMethods().forEach(m -> addInheritedMethod(m, actualMethods, actualNameContext));
    addInheritedMethods(parentType, actualMethods, actualNameContext);
  }

  private static void addInheritedMethod(
      MethodStatement addedMethod, List<MethodStatement> actualMethods, TypeContext typeContext
  ) {
    MethodStatement effectiveAddedMethod = getEffectiveMethod(addedMethod, typeContext);
    MethodSignature effectiveAddedSignature = effectiveAddedMethod.signature();
    for (MethodStatement method : actualMethods) {
      MethodSignature methodSignature = method.signature();
      if (effectiveAddedSignature.name().equals(methodSignature.name()) &&
          isSameParams(effectiveAddedSignature, methodSignature)
      ) {
        return;
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
    return getTypeParametersDeclaration(customType, fullDeclaration, Function.identity());
  }

  static String getTypeParametersDeclaration(
      CustomType customType, boolean fullDeclaration, Function<String, String> nameMapper
  ) {
    var parametersSource = customType.typeParameters().stream()
        .map(param -> TypeReferenceFunctions.getNamedTypeReferenceDeclaration(param, false, fullDeclaration, nameMapper))
        .collect(Collectors.joining(", "));
    return (parametersSource.isEmpty() ? "" : "<" + parametersSource + ">");
  }
}
