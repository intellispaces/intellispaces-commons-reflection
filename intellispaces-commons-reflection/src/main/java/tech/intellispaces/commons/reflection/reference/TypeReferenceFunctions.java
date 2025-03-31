package tech.intellispaces.commons.reflection.reference;

import tech.intellispaces.commons.action.runnable.RunnableAction;
import tech.intellispaces.commons.action.text.StringActions;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.commons.reflection.StatementTypes;
import tech.intellispaces.commons.reflection.customtype.CustomType;
import tech.intellispaces.commons.reflection.customtype.CustomTypeFunctions;
import tech.intellispaces.commons.reflection.exception.JavaStatementExceptions;
import tech.intellispaces.commons.type.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TypeReferenceFunctions {

  static Class<?> getClass(TypeReference typeReference) {
    if (StatementTypes.PrimitiveReference.equals(typeReference.statementType())) {
      return typeReference.asPrimitiveReferenceOrElseThrow().primitiveType().wrapperClass();
    } else if (StatementTypes.CustomReference.equals(typeReference.statementType())) {
      return getClass(typeReference.asCustomTypeReference().orElseThrow().targetType().canonicalName());
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported type {0}", typeReference.statementType().typename());
    }
  }

  static Class<?> getClass(String className) {
    final Class<?> aClass;
    try {
      aClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Class " + className + " is not found", e);
    }
    return aClass;
  }

  static Optional<TypeReference> narrowestOf(
      TypeReference typeReferenceReference1, TypeReference typeReferenceReference2
  ) {
    if (typeReferenceReference1.asPrimitiveReference().isPresent() && typeReferenceReference2.asPrimitiveReference().isPresent()) {
      String typename1 = typeReferenceReference1.asPrimitiveReferenceOrElseThrow().primitiveType().typename();
      String typename2 = typeReferenceReference2.asPrimitiveReferenceOrElseThrow().primitiveType().typename();
      if (typename1.equals(typename2)) {
        return Optional.of(typeReferenceReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReferenceReference1.asNamedReference().isPresent() && typeReferenceReference2.asNamedReference().isPresent()) {
      NamedReference type1 = typeReferenceReference1.asNamedReference().orElseThrow();
      NamedReference type2 = typeReferenceReference2.asNamedReference().orElseThrow();
      if (type1.name().equals(type2.name())) {
        return Optional.of(typeReferenceReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReferenceReference1.asCustomTypeReference().isPresent() && typeReferenceReference2.asCustomTypeReference().isPresent()) {
      CustomType type1 = typeReferenceReference1.asCustomTypeReference().orElseThrow().targetType();
      CustomType type2 = typeReferenceReference2.asCustomTypeReference().orElseThrow().targetType();
      if (allParentCanonicalNames(type1).contains(type2.canonicalName())) {
        return Optional.of(typeReferenceReference1);
      } else if (allParentCanonicalNames(type2).contains(type1.canonicalName())) {
        return Optional.of(typeReferenceReference2);
      } else {
        return Optional.empty();
      }
    } else if (typeReferenceReference1.asNamedReference().isPresent() && typeReferenceReference2.asCustomTypeReference().isPresent()) {
      return Optional.of(typeReferenceReference2);
    } else if (typeReferenceReference1.asCustomTypeReference().isPresent() && typeReferenceReference2.asNamedReference().isPresent()) {
      return Optional.of(typeReferenceReference1);
    } else {
      return Optional.empty();
    }
  }

  private static List<String> allParentCanonicalNames(CustomType customType) {
    List<CustomType> types = new ArrayList<>();
    types.add(customType);
    types.addAll(CustomTypeFunctions.allParents(customType));
    return types.stream()
        .map(CustomType::canonicalName)
        .toList();
  }

  static boolean isEqualTypes(List<TypeReference> types1, List<TypeReference> types2) {
    if (types1.size() != types2.size()) {
      throw UnexpectedExceptions.withMessage("Expected two lists with equal size");
    }
    Iterator<TypeReference> iteratorTypes1 = types1.iterator();
    Iterator<TypeReference> iteratorTypes2 = types2.iterator();
    while (iteratorTypes1.hasNext()) {
      TypeReference typeReference1 = iteratorTypes1.next();
      TypeReference typeReference2 = iteratorTypes2.next();
      if (!isEqualTypes(typeReference1, typeReference2)) {
        return false;
      }
    }
    return true;
  }

  static boolean isEqualTypes(TypeReference typeReference1, TypeReference typeReference2) {
    if (typeReference1.isPrimitiveReference() && typeReference2.isPrimitiveReference()) {
      PrimitiveReference primitiveReference1 = typeReference1.asPrimitiveReferenceOrElseThrow();
      PrimitiveReference primitiveReference2 = typeReference2.asPrimitiveReferenceOrElseThrow();
      return isEqualPrimitiveTypeReferences(primitiveReference1, primitiveReference2);
    } else if (typeReference1.isCustomTypeReference() && typeReference2.isCustomTypeReference()) {
      CustomTypeReference customTypeReference1 = typeReference1.asCustomTypeReferenceOrElseThrow();
      CustomTypeReference customTypeReference2 = typeReference2.asCustomTypeReferenceOrElseThrow();
      return isEqualCustomTypeReferences(customTypeReference1, customTypeReference2);
    } else if (typeReference1.isNamedReference() && typeReference2.isNamedReference()) {
      NamedReference namedReference1 = typeReference1.asNamedReferenceOrElseThrow();
      NamedReference namedReference2 = typeReference2.asNamedReferenceOrElseThrow();
      return isEqualNamedTypeReferences(namedReference1, namedReference2);
    } else if (typeReference1.isArrayReference() && typeReference2.isArrayReference()) {
      ArrayReference arrayReference1 = typeReference1.asArrayReferenceOrElseThrow();
      ArrayReference arrayReference2 = typeReference2.asArrayReferenceOrElseThrow();
      return isEqualArrayTypeReferences(arrayReference1, arrayReference2);
    } else if (typeReference1.isWildcard() && typeReference2.isWildcard()) {
      WildcardReference wildcardReference1 = typeReference1.asWildcardOrElseThrow();
      WildcardReference wildcardReference2 = typeReference2.asWildcardOrElseThrow();
      return isEqualWildcardReferences(wildcardReference1, wildcardReference2);
    } else {
      return false;
    }
  }

  private static boolean isEqualPrimitiveTypeReferences(
      PrimitiveReference typeReference1, PrimitiveReference typeReference2
  ) {
    return typeReference1.primitiveType().typename().equals(typeReference2.primitiveType().typename());
  }

  private static boolean isEqualCustomTypeReferences(
      CustomTypeReference typeReference1, CustomTypeReference typeReference2
  ) {
    if (!typeReference1.targetType().canonicalName().equals(typeReference2.targetType().canonicalName())) {
      return false;
    }

    Iterator<NotPrimitiveReference> typeReferenceArguments1 = typeReference1.typeArguments().iterator();
    Iterator<NotPrimitiveReference> typeReferenceArguments2 = typeReference2.typeArguments().iterator();
    while (typeReferenceArguments1.hasNext() && typeReferenceArguments2.hasNext()) {
      NotPrimitiveReference typeReferenceArgument1 = typeReferenceArguments1.next();
      NotPrimitiveReference typeReferenceArgument2 = typeReferenceArguments2.next();
      if (!isEqualTypes(typeReferenceArgument1, typeReferenceArgument2)) {
        return false;
      }
    }
    if (typeReferenceArguments1.hasNext() || typeReferenceArguments2.hasNext()) {
      return false;
    }
    return true;
  }

  static boolean isEqualNamedTypeReferences(NamedReference typeReference1, NamedReference typeReference2) {
    if (typeReference1.extendedBounds().size() != typeReference2.extendedBounds().size()) {
      return false;
    }
    Iterator<ReferenceBound> bounds1 = typeReference1.extendedBounds().iterator();
    Iterator<ReferenceBound> bounds2 = typeReference2.extendedBounds().iterator();
    while (bounds1.hasNext() && bounds2.hasNext()) {
      if (!isEqualTypes(bounds1.next(), bounds2.next())) {
        return false;
      }
    }
    return true;
  }

  static boolean isEqualArrayTypeReferences(ArrayReference typeReference1, ArrayReference typeReference2) {
    return isEqualTypes(typeReference1.elementType(), typeReference2.elementType());
  }

  static boolean isEqualWildcardReferences(WildcardReference wildcardReference1, WildcardReference wildcardReference2) {
    if (wildcardReference1.extendedBound().isPresent() && wildcardReference2.extendedBound().isPresent()) {
      TypeReference extendedBound1 = wildcardReference1.extendedBound().get();
      TypeReference extendedBound2 = wildcardReference1.extendedBound().get();
      if (!isEqualTypes(extendedBound1, extendedBound2)) {
        return false;
      }
    } else if (wildcardReference1.extendedBound().isPresent() || wildcardReference2.extendedBound().isPresent()) {
      return false;
    }

    if (wildcardReference1.superBound().isPresent() && wildcardReference2.superBound().isPresent()) {
      TypeReference superBound1 = wildcardReference1.superBound().get();
      TypeReference superBound2 = wildcardReference1.superBound().get();
      if (!isEqualTypes(superBound1, superBound2)) {
        return false;
      }
    } else if (wildcardReference1.superBound().isPresent() || wildcardReference2.superBound().isPresent()) {
      return false;
    }

    return true;
  }

  static boolean isEquivalentTypes(TypeReference typeReference1, TypeReference typeReference2) {
    if (typeReference1.isPrimitiveReference() && typeReference2.isPrimitiveReference()) {
      PrimitiveReference primitiveTypeReference1 = typeReference1.asPrimitiveReferenceOrElseThrow();
      PrimitiveReference primitiveTypeReference2 = typeReference2.asPrimitiveReferenceOrElseThrow();
      return primitiveTypeReference1.primitiveType().typename().equals(primitiveTypeReference2.primitiveType().typename());
    } else if (typeReference1.isCustomTypeReference() && typeReference2.isCustomTypeReference()) {
      CustomType customType1 = typeReference1.asCustomTypeReferenceOrElseThrow().targetType();
      CustomType customType2 = typeReference2.asCustomTypeReferenceOrElseThrow().targetType();
      return customType1.canonicalName().equals(customType2.canonicalName());
    } else if (typeReference1.isNamedReference() && typeReference2.isNamedReference()) {
      NamedReference namedReference1 = typeReference1.asNamedReferenceOrElseThrow();
      NamedReference namedReference2 = typeReference2.asNamedReferenceOrElseThrow();
      return isEqualNamedTypeReferences(namedReference1, namedReference2);
    } else if (
        (typeReference1.isPrimitiveReference() && typeReference2.isCustomTypeReference()) ||
            (typeReference1.isCustomTypeReference() && typeReference2.isPrimitiveReference()) ||
            (typeReference1.isNamedReference() && typeReference2.isCustomTypeReference()) ||
            (typeReference1.isCustomTypeReference() && typeReference2.isNamedReference())
    ) {
      return false;
    } else {
      throw NotImplementedExceptions.withCode("icwJwg");
    }
  }

  /**
   * Returns actual type declaration.
   */
  static String getActualTypeDeclaration(TypeReference typeReference) {
    return getActualTypeDeclaration(typeReference, false, false);
  }

  static String getActualTypeDeclaration(
      TypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeDeclaration(typeReference, false, false, simpleNameMapper);
  }

  static String getActualBlindTypeReferenceDeclaration(TypeReference typeReference) {
    return getActualTypeDeclaration(typeReference, true, false);
  }

  static String getActualBlindTypeReferenceDeclaration(
      TypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeDeclaration(typeReference, true, false, simpleNameMapper);
  }

  static String getActualRawTypeReferenceDeclaration(
      TypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeDeclaration(typeReference, false, true, simpleNameMapper);
  }

  private static String getActualTypeDeclaration(TypeReference typeReference, boolean blind, boolean raw) {
    return getActualTypeDeclaration(typeReference, blind, raw, Function.identity());
}

  static String getSimpleTypeDeclaration(TypeReference typeReference) {
    return getSimpleTypeDeclaration(typeReference, ClassNameFunctions::getSimpleName);
  }

  static String getSimpleTypeDeclaration(
      TypeReference typeReference, Function<String, String> nameMapper
  ) {
    if (typeReference.asPrimitiveReference().isPresent()) {
      return typeReference.asPrimitiveReference().get().primitiveType().typename();
    } else if (typeReference.asArrayReference().isPresent()) {
      TypeReference elementTypeReference = typeReference.asArrayReference().get().elementType();
      return getSimpleTypeDeclaration(elementTypeReference) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      return nameMapper.apply(customType.canonicalName());
    } else if (typeReference.asNamedReference().isPresent()) {
      return typeReference.asNamedReferenceOrElseThrow().name();
    } else if (typeReference.asWildcard().isPresent()) {
      return "?";
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported type {0}", typeReference.statementType().typename());
    }
  }

  /**
   * Returns actual type declaration.
   */
  private static String getActualTypeDeclaration(
      TypeReference typeReference, boolean blind, boolean raw, Function<String, String> nameMapper
  ) {
    if (typeReference.asPrimitiveReference().isPresent()) {
      return typeReference.asPrimitiveReference().get().primitiveType().typename();
    } else if (typeReference.asArrayReference().isPresent()) {
      TypeReference elementTypeReference = typeReference.asArrayReference().get().elementType();
      return getActualTypeDeclaration(elementTypeReference, blind, raw) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      String actualName = nameMapper.apply(customType.canonicalName());
      if (raw) {
        return actualName;
      }
      return actualName + getTypeArgumentsDeclaration(typeReference.asCustomTypeReference().get(), blind, nameMapper);
    } else if (typeReference.asNamedReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedReference().get(), blind, raw, false);
    } else if (typeReference.asWildcard().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcard().get(), blind, raw, true);
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported type {0}", typeReference.statementType().typename());
    }
  }

  static String getFormalFullTypeReferenceDeclaration(TypeReference typeReference) {
    return getFormalTypeReferenceDeclaration(typeReference, true);
  }

  static String getFormalBriefTypeReferenceDeclaration(TypeReference typeReference) {
    return getFormalTypeReferenceDeclaration(typeReference, false);
  }

  static String getFormalTypeReferenceDeclaration(TypeReference typeReference, boolean fullDeclaration) {
    if (typeReference.asPrimitiveReference().isPresent()) {
      return typeReference.asPrimitiveReference().get().primitiveType().typename();
    } else if (typeReference.asArrayReference().isPresent()) {
      TypeReference elementTypeReference = typeReference.asArrayReference().get().elementType();
      return getFormalTypeReferenceDeclaration(elementTypeReference, fullDeclaration) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      return customType.simpleName() + CustomTypeFunctions.getTypeParametersDeclaration(customType, fullDeclaration);
    } else if (typeReference.asNamedReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedReference().get(), false, false, fullDeclaration);
    } else if (typeReference.asWildcard().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcard().get(), false, false, fullDeclaration);
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported type {0}", typeReference.statementType().typename());
    }
  }

  static String getTypeArgumentsDeclaration(CustomTypeReference typeReference) {
    return getTypeArgumentsDeclaration(typeReference, false, Function.identity());
  }

  static String getTypeArgumentsDeclaration(
      CustomTypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getTypeArgumentsDeclaration(typeReference, false, simpleNameMapper);
  }

  static String getTypeArgumentsDeclaration(
      CustomTypeReference typeReference, boolean blind, Function<String, String> simpleNameMapper
  ) {
    String arguments = typeReference.typeArguments().stream()
        .map(t -> getActualTypeDeclaration(t, blind, false, simpleNameMapper))
        .collect(Collectors.joining(", "));
    return (arguments.isEmpty() ? "" : "<" + arguments + ">");
  }

  static String getNamedTypeReferenceDeclaration(NamedReference typeReference, boolean fullDeclaration) {
    return getNamedTypeReferenceDeclaration(typeReference, false, false, fullDeclaration);
  }

  static String getNamedTypeReferenceDeclaration(
      NamedReference typeReference, boolean blind, boolean raw, boolean fullDeclaration
  ) {
    return getNamedTypeReferenceDeclaration(typeReference, blind, raw, fullDeclaration, Function.identity());
  }

  static String getNamedTypeReferenceDeclaration(
      NamedReference typeReference, boolean blind, boolean raw, boolean fullDeclaration, Function<String, String> nameMapper
  ) {
    if (raw) {
      if (typeReference.extendedBounds().isEmpty()) {
        return nameMapper.apply(Object.class.getCanonicalName());
      } else {
        ReferenceBound extendedTypeReference = typeReference.extendedBounds().get(0);
        return getActualTypeDeclaration(extendedTypeReference, blind, true, nameMapper);
      }
    }
    if (!fullDeclaration || typeReference.extendedBounds().isEmpty()) {
      return blind ? "?" : typeReference.name();
    } else {
      var sb = new StringBuilder();
      boolean first = true;
      for (ReferenceBound extendedTypeReference : typeReference.extendedBounds()) {
        if (!first) {
          sb.append(" & ");
        }
        first = false;
        sb.append(getActualTypeDeclaration(extendedTypeReference, blind, raw, nameMapper));
      }
      return (blind ? "?" : typeReference.name()) + " extends " + sb;
    }
  }

  static String getWildcardDeclaration(
      WildcardReference typeReference, boolean blind, boolean raw, boolean fullDeclaration
  ) {
    if (raw) {
      return Object.class.getSimpleName();
    }
    if (!fullDeclaration) {
      return "?";
    } else {
      var sb = new StringBuilder();
      sb.append("?");
      if (typeReference.extendedBound().isPresent()) {
        sb.append(" extends ");
        sb.append(getActualTypeDeclaration(typeReference.extendedBound().get(), blind, false));
      }
      if (typeReference.superBound().isPresent()) {
        sb.append(" super ");
        sb.append(getActualTypeDeclaration(typeReference.superBound().get(), blind, false));
      }
      return sb.toString();
    }
  }

  static String getTypeExpression(TypeReference typeReference, Function<String, String> nameMapper) {
    var sb = new StringBuilder();
    getTypeExpression(typeReference, sb, true, true, nameMapper);
    return sb.toString();
  }

  private static void getTypeExpression(
      TypeReference type,
      StringBuilder sb,
      boolean includeTypesPrefix,
      boolean includeClassPostfix,
      Function<String, String> nameMapper
  ) {
    if (includeTypesPrefix) {
      sb.append(nameMapper.apply(Types.class.getCanonicalName()));
      sb.append(".get(");
    }

    if (type.isCustomTypeReference()) {
      CustomTypeReference customTypeReference = type.asCustomTypeReferenceOrElseThrow();
      sb.append(nameMapper.apply(customTypeReference.targetType().canonicalName()));
      if (includeClassPostfix) {
        sb.append(".class");
      }
      if (!customTypeReference.typeArguments().isEmpty()) {
        sb.append(", ");
        RunnableAction commaAppender = StringActions.skipFirstTimeCommaAppender(sb);
        for (NotPrimitiveReference typeArgument : customTypeReference.typeArguments()) {
          commaAppender.run();
          getTypeExpression(typeArgument, sb, true, true, nameMapper);
        }
      }
    } else if (type.isPrimitiveReference()) {
      PrimitiveReference primitiveReference = type.asPrimitiveReferenceOrElseThrow();
      sb.append(primitiveReference.typename());
      if (includeClassPostfix) {
        sb.append(".class");
      }
    } else if (type.isNamedReference()) {
      NamedReference namedReference = type.asNamedReferenceOrElseThrow();
      if (namedReference.extendedBounds().isEmpty()) {
        sb.append(nameMapper.apply(Object.class.getCanonicalName()));
        if (includeClassPostfix) {
          sb.append(".class");
        }
      } else {
        getTypeExpression(
            namedReference.extendedBounds().get(0), sb, includeTypesPrefix, includeClassPostfix, nameMapper
        );
      }
    } else if (type.isArrayReference()) {
      ArrayReference arrayReference = type.asArrayReferenceOrElseThrow();
      getTypeExpression(arrayReference.elementType(), sb, false, false, nameMapper);
      sb.append("[].class");
    } else {
      throw NotImplementedExceptions.withCode("WFRyVXBa");
    }

    if (includeTypesPrefix) {
      sb.append(")");
    }
  }

  static Map<String, NotPrimitiveReference> getTypeArgumentMapping(
      CustomTypeReference customTypeReference
  ) {
    List<NotPrimitiveReference> typeArguments = customTypeReference.typeArguments();
    List<NamedReference> typeParams = customTypeReference.targetType().typeParameters();
    if (typeArguments.isEmpty() && typeParams.isEmpty()) {
      return Map.of();
    }
    if (typeArguments.size() != typeParams.size()) {
      throw UnexpectedExceptions.withMessage("Number of type arguments {0} does not match with " +
          "number of type parameters {1}. See type {2}",
          typeArguments.size(), typeParams.size(), customTypeReference.formalFullDeclaration());
    }

    Map<String, NotPrimitiveReference> mapping = new HashMap<>();
    Iterator<NotPrimitiveReference> typeArgumentIterator = customTypeReference.typeArguments().iterator();
    Iterator<NamedReference> typeParamIterator = customTypeReference.targetType().typeParameters().iterator();
    while (typeArgumentIterator.hasNext()) {
      NotPrimitiveReference typeArgument = typeArgumentIterator.next();
      NamedReference typeParam = typeParamIterator.next();
      mapping.put(typeParam.name(), typeArgument);
    }
    return mapping;
  }

  static Map<String, NotPrimitiveReference> getTypeArgumentMapping(
      CustomType originType, CustomType targetType
  ) {
    if (targetType.typeParameters().isEmpty()) {
      return Map.of();
    }

    for (CustomTypeReference parent : originType.parentTypes()) {
      if (parent.targetType().canonicalName().equals(targetType.canonicalName())) {
        return getTypeArgumentMapping(parent);
      }
      if (parent.targetType().hasParent(targetType)) {
        Map<String, NotPrimitiveReference> typeArgumentMapping = getTypeArgumentMapping(parent);
        return getTypeArgumentMapping(parent.targetType(), targetType, typeArgumentMapping);
      }
    }
    return Map.of();
  }

  private static Map<String, NotPrimitiveReference> getTypeArgumentMapping(
      CustomType originType,
      CustomType targetType,
      Map<String, NotPrimitiveReference> initialTypeArgumentMapping
  ) {
    for (CustomTypeReference parent : originType.parentTypes()) {
      if (parent.targetType().canonicalName().equals(targetType.canonicalName())) {
        return mergeTypeArgumentMapping(parent, initialTypeArgumentMapping);
      }
      if (parent.targetType().hasParent(targetType)) {
        return getTypeArgumentMapping(
            parent.targetType(), targetType, mergeTypeArgumentMapping(parent, initialTypeArgumentMapping)
        );
      }
    }
    return Map.of();
  }

  private static Map<String, NotPrimitiveReference> mergeTypeArgumentMapping(
      CustomTypeReference typeReference,
      Map<String, NotPrimitiveReference> typeArgumentMapping
  ) {
    Map<String, NotPrimitiveReference> mapping = new HashMap<>();
    Iterator<NotPrimitiveReference> typeArgumentIterator = typeReference.typeArguments().iterator();
    Iterator<NamedReference> typeParamIterator = typeReference.targetType().typeParameters().iterator();
    while (typeArgumentIterator.hasNext()) {
      NotPrimitiveReference typeArgument = typeArgumentIterator.next();
      if (typeArgument.isNamedReference()) {
        String name = typeArgument.asNamedReference().orElseThrow().name();
        NotPrimitiveReference value = typeArgumentMapping.get(name);
        if (value != null) {
          typeArgument = value;
        }
      }
      NamedReference typeParam = typeParamIterator.next();
      mapping.put(typeParam.name(), typeArgument);
    }
    return mapping;
  }
}
