package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.commons.type.TypeFunctions;
import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.custom.CustomTypeFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TypeReferenceFunctions {

  static Optional<TypeReference> narrowestOf(TypeReference typeReference1, TypeReference typeReference2) {
    if (typeReference1.asPrimitiveTypeReference().isPresent() && typeReference2.asPrimitiveTypeReference().isPresent()) {
      String typename1 = typeReference1.asPrimitiveTypeReference().orElseThrow().typename();
      String typename2 = typeReference2.asPrimitiveTypeReference().orElseThrow().typename();
      if (typename1.equals(typename2)) {
        return Optional.of(typeReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asNamedTypeReference().isPresent() && typeReference2.asNamedTypeReference().isPresent()) {
      NamedTypeReference type1 = typeReference1.asNamedTypeReference().orElseThrow();
      NamedTypeReference type2 = typeReference2.asNamedTypeReference().orElseThrow();
      if (type1.name().equals(type2.name())) {
        return Optional.of(typeReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asCustomTypeReference().isPresent() && typeReference2.asCustomTypeReference().isPresent()) {
      CustomType type1 = typeReference1.asCustomTypeReference().orElseThrow().targetType();
      CustomType type2 = typeReference2.asCustomTypeReference().orElseThrow().targetType();
      if (allTypes(type1).contains(type2.canonicalName())) {
        return Optional.of(typeReference1);
      } else if (allTypes(type2).contains(type1.canonicalName())) {
        return Optional.of(typeReference2);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asNamedTypeReference().isPresent() && typeReference2.asCustomTypeReference().isPresent()) {
      return Optional.of(typeReference2);
    } else if (typeReference1.asCustomTypeReference().isPresent() && typeReference2.asNamedTypeReference().isPresent()) {
      return Optional.of(typeReference1);
    } else {
      return Optional.empty();
    }
  }

  private static List<String> allTypes(CustomType customType) {
    List<CustomType> types = new ArrayList<>();
    types.add(customType);
    types.addAll(CustomTypeFunctions.allParents(customType));
    return types.stream()
        .map(CustomType::canonicalName)
        .toList();
  }

  static boolean isEqualTypes(List<TypeReference> types1, List<TypeReference> types2) {
    if (types1.size() != types2.size()) {
      throw UnexpectedViolationException.withMessage("Expected two lists with equal size");
    }
    Iterator<TypeReference> iteratorTypes1 = types1.iterator();
    Iterator<TypeReference> iteratorTypes2 = types2.iterator();
    while (iteratorTypes1.hasNext()) {
      TypeReference type1 = iteratorTypes1.next();
      TypeReference type2 = iteratorTypes2.next();
      if (!isEqualTypes(type1, type2)) {
        return false;
      }
    }
    return true;
  }

  static boolean isEqualTypes(TypeReference typeReference1, TypeReference typeReference2) {
    boolean sameParams = true;
    if (typeReference1.isPrimitive() && typeReference2.isPrimitive()) {
      if (!Objects.equals(
          typeReference1.asPrimitiveTypeReference().orElseThrow().typename(),
          typeReference2.asPrimitiveTypeReference().orElseThrow().typename()
      )) {
        sameParams = false;
      }
    } else if (typeReference1.asCustomTypeReference().isPresent() && typeReference2.asCustomTypeReference().isPresent()) {
      if (!Objects.equals(
          typeReference1.asCustomTypeReference().orElseThrow().targetType().canonicalName(),
          typeReference2.asCustomTypeReference().orElseThrow().targetType().canonicalName()
      )) {
        sameParams = false;
      }
    } else if (typeReference1.asNamedTypeReference().isPresent() && typeReference2.asNamedTypeReference().isPresent()) {
      var namedTypeReference1 = typeReference1.asNamedTypeReference().get();
      var namedTypeReference2 = typeReference2.asNamedTypeReference().get();
      if (!namedTypeReference1.extendedBounds().isEmpty() || !namedTypeReference2.extendedBounds().isEmpty()) {
        // todo: compare extended bounds
        throw new UnsupportedOperationException("Not implemented yet");
      }
    } else {
      sameParams = false;
    }
    return sameParams;
  }

  static boolean isEquivalentTypes(TypeReference type1, TypeReference type2) {
    if (type1.isPrimitive() && type2.isPrimitive()) {
      PrimitiveTypeReference primitiveType1 = type1.asPrimitiveTypeReferenceSurely();
      PrimitiveTypeReference primitiveType2 = type2.asPrimitiveTypeReferenceSurely();
      return primitiveType1.typename().equals(primitiveType2.typename());
    } else if (type1.isCustomTypeReference() && type2.isCustomTypeReference()) {
      CustomType customType1 = type1.asCustomTypeReferenceSurely().targetType();
      CustomType customType2 = type2.asCustomTypeReferenceSurely().targetType();
      return customType1.canonicalName().equals(customType2.canonicalName());
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented");
    }
  }

  /**
   * Returns actual type reference declaration.
   */
  static String getActualTypeReferenceDeclaration(TypeReference typeReference) {
    return getActualTypeReferenceDeclaration(typeReference, false);
  }

  static String getActualTypeReferenceDeclaration(
      TypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeReferenceDeclaration(typeReference, false, simpleNameMapper);
  }

  static String getActualBlindTypeReferenceDeclaration(TypeReference typeReference) {
    return getActualTypeReferenceDeclaration(typeReference, true);
  }

  static String getActualBlindTypeReferenceDeclaration(
      TypeReference typeReference, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeReferenceDeclaration(typeReference, true, simpleNameMapper);
  }

  private static String getActualTypeReferenceDeclaration(TypeReference typeReference, boolean blind) {
    return getActualTypeReferenceDeclaration(typeReference, blind, TypeFunctions::getSimpleName);
  }

  /**
   * Returns actual type reference declaration.
   */
  private static String getActualTypeReferenceDeclaration(
      TypeReference typeReference, boolean blind, Function<String, String> simpleNameMapper
  ) {
    if (typeReference.asPrimitiveTypeReference().isPresent()) {
      return typeReference.asPrimitiveTypeReference().get().typename();
    } else if (typeReference.asArrayTypeReference().isPresent()) {
      TypeReference elementType = typeReference.asArrayTypeReference().get().elementType();
      return getActualTypeReferenceDeclaration(elementType, blind) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      String simpleName = simpleNameMapper.apply(customType.canonicalName());
      return simpleName + getTypeArgumentsDeclaration(typeReference.asCustomTypeReference().get(), blind, simpleNameMapper);
    } else if (typeReference.asNamedTypeReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedTypeReference().get(), blind, false);
    } else if (typeReference.asWildcardTypeReference().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcardTypeReference().get(), blind, true);
    } else {
      throw JavaStatementException.withMessage("Unsupported reference type {}", typeReference.statementType().typename());
    }
  }

  static String getFormalFullTypeReferenceDeclaration(TypeReference typeReference) {
    return getFormalTypeReferenceDeclaration(typeReference, true);
  }

  static String getFormalBriefTypeReferenceDeclaration(TypeReference typeReference) {
    return getFormalTypeReferenceDeclaration(typeReference, false);
  }

  static String getFormalTypeReferenceDeclaration(TypeReference typeReference, boolean fullDeclaration) {
    if (typeReference.asPrimitiveTypeReference().isPresent()) {
      return typeReference.asPrimitiveTypeReference().get().typename();
    } else if (typeReference.asArrayTypeReference().isPresent()) {
      TypeReference elementType = typeReference.asArrayTypeReference().get().elementType();
      return getFormalTypeReferenceDeclaration(elementType, fullDeclaration) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      return customType.simpleName() + CustomTypeFunctions.getTypeParametersDeclaration(customType, fullDeclaration);
    } else if (typeReference.asNamedTypeReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedTypeReference().get(), false, fullDeclaration);
    } else if (typeReference.asWildcardTypeReference().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcardTypeReference().get(), false, fullDeclaration);
    } else {
      throw JavaStatementException.withMessage("Unsupported reference type {}", typeReference.statementType().typename());
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
        .map(t -> getActualTypeReferenceDeclaration(t, blind, simpleNameMapper))
        .collect(Collectors.joining(", "));
    return (arguments.isEmpty() ? "" : "<" + arguments + ">");
  }

  static String getNamedTypeReferenceDeclaration(NamedTypeReference typeReference, boolean fullDeclaration) {
    return getNamedTypeReferenceDeclaration(typeReference, false, fullDeclaration);
  }

  static String getNamedTypeReferenceDeclaration(
      NamedTypeReference typeReference, boolean blind, boolean fullDeclaration
  ) {
    if (!fullDeclaration || typeReference.extendedBounds().isEmpty()) {
      return blind ? "?" : typeReference.name();
    } else {
      var sb = new StringBuilder();
      boolean first = true;
      for (TypeBoundReference extendedTypeReference : typeReference.extendedBounds()) {
        if (!first) {
          sb.append(" & ");
        }
        first = false;
        sb.append(getActualTypeReferenceDeclaration(extendedTypeReference, blind));
      }
      return (blind ? "?" : typeReference.name()) + " extends " + sb;
    }
  }

  static String getWildcardDeclaration(WildcardTypeReference typeReference, boolean blind, boolean fullDeclaration) {
    if (!fullDeclaration) {
      return "?";
    } else {
      var sb = new StringBuilder();
      sb.append("?");
      if (typeReference.extendedBound().isPresent()) {
        sb.append(" extends ");
        sb.append(getActualTypeReferenceDeclaration(typeReference.extendedBound().get(), blind));
      }
      if (typeReference.superBound().isPresent()) {
        sb.append(" super ");
        sb.append(getActualTypeReferenceDeclaration(typeReference.superBound().get(), blind));
      }
      return sb.toString();
    }
  }

  static Map<String, NonPrimitiveTypeReference> getTypeArgumentMapping(CustomTypeReference customTypeReference) {
    List<NonPrimitiveTypeReference> typeArguments = customTypeReference.typeArguments();
    List<NamedTypeReference> typeParams = customTypeReference.targetType().typeParameters();
    if (typeArguments.isEmpty() && typeParams.isEmpty()) {
      return Map.of();
    }
    if (typeArguments.size() != typeParams.size()) {
      throw UnexpectedViolationException.withMessage("Number of type arguments {} does not match with " +
          "number of type parameters {}. See reference {}",
          typeArguments.size(), typeParams.size(), customTypeReference.formalFullDeclaration());
    }

    Map<String, NonPrimitiveTypeReference> mapping = new HashMap<>();
    Iterator<NonPrimitiveTypeReference> typeArgumentIterator = customTypeReference.typeArguments().iterator();
    Iterator<NamedTypeReference> typeParamIterator = customTypeReference.targetType().typeParameters().iterator();
    while (typeArgumentIterator.hasNext()) {
      NonPrimitiveTypeReference typeArgument = typeArgumentIterator.next();
      NamedTypeReference typeParam = typeParamIterator.next();
      mapping.put(typeParam.name(), typeArgument);
    }
    return mapping;
  }
}
