package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomTypeFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TypeFunctions {

  static Optional<Type> narrowestOf(Type typeReference1, Type typeReference2) {
    if (typeReference1.asPrimitive().isPresent() && typeReference2.asPrimitive().isPresent()) {
      String typename1 = typeReference1.asPrimitive().orElseThrow().typename();
      String typename2 = typeReference2.asPrimitive().orElseThrow().typename();
      if (typename1.equals(typename2)) {
        return Optional.of(typeReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asNamed().isPresent() && typeReference2.asNamed().isPresent()) {
      NamedType type1 = typeReference1.asNamed().orElseThrow();
      NamedType type2 = typeReference2.asNamed().orElseThrow();
      if (type1.name().equals(type2.name())) {
        return Optional.of(typeReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asCustom().isPresent() && typeReference2.asCustom().isPresent()) {
      CustomStatement type1 = typeReference1.asCustom().orElseThrow().statement();
      CustomStatement type2 = typeReference2.asCustom().orElseThrow().statement();
      if (allTypes(type1).contains(type2.canonicalName())) {
        return Optional.of(typeReference1);
      } else if (allTypes(type2).contains(type1.canonicalName())) {
        return Optional.of(typeReference2);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asNamed().isPresent() && typeReference2.asCustom().isPresent()) {
      return Optional.of(typeReference2);
    } else if (typeReference1.asCustom().isPresent() && typeReference2.asNamed().isPresent()) {
      return Optional.of(typeReference1);
    } else {
      return Optional.empty();
    }
  }

  private static List<String> allTypes(CustomStatement customStatement) {
    List<CustomStatement> types = new ArrayList<>();
    types.add(customStatement);
    types.addAll(CustomTypeFunctions.allParents(customStatement));
    return types.stream()
        .map(CustomStatement::canonicalName)
        .toList();
  }

  static boolean isEqualTypes(List<Type> types1, List<Type> types2) {
    if (types1.size() != types2.size()) {
      throw UnexpectedViolationException.withMessage("Expected two lists with equal size");
    }
    Iterator<Type> iteratorTypes1 = types1.iterator();
    Iterator<Type> iteratorTypes2 = types2.iterator();
    while (iteratorTypes1.hasNext()) {
      Type type1 = iteratorTypes1.next();
      Type type2 = iteratorTypes2.next();
      if (!isEqualTypes(type1, type2)) {
        return false;
      }
    }
    return true;
  }

  static boolean isEqualTypes(Type type1, Type type2) {
    if (type1.isPrimitive() && type2.isPrimitive()) {
      PrimitiveType primitiveTypeReference1 = type1.asPrimitiveConfidently();
      PrimitiveType primitiveTypeReference2 = type2.asPrimitiveConfidently();
      return isEqualPrimitiveTypeReferences(primitiveTypeReference1, primitiveTypeReference2);
    } else if (type1.isCustom() && type2.isCustom()) {
      CustomType customTypeReference1 = type1.asCustomConfidently();
      CustomType customTypeReference2 = type2.asCustomConfidently();
      return isEqualCustomTypeReferences(customTypeReference1, customTypeReference2);
    } else if (type1.isNamed() && type2.isNamed()) {
      NamedType namedTypeReference1 = type1.asNamedConfidently();
      NamedType namedTypeReference2 = type2.asNamedConfidently();
      return isEqualNamedTypeReferences(namedTypeReference1, namedTypeReference2);
    } else {
      return false;
    }
  }

  private static boolean isEqualPrimitiveTypeReferences(
      PrimitiveType typeReference1, PrimitiveType typeReference2
  ) {
    return typeReference1.typename().equals(typeReference2.typename());
  }

  private static boolean isEqualCustomTypeReferences(
      CustomType typeReference1, CustomType typeReference2
  ) {
    return typeReference1.statement().canonicalName().equals(
        typeReference2.statement().canonicalName()
    );
  }

  static boolean isEqualNamedTypeReferences(
      NamedType typeReference1, NamedType typeReference2
  ) {
    if (typeReference1.extendedBounds().size() != typeReference2.extendedBounds().size()) {
      return false;
    }
    Iterator<TypeBound> bounds1 = typeReference1.extendedBounds().iterator();
    Iterator<TypeBound> bounds2 = typeReference2.extendedBounds().iterator();
    while (bounds1.hasNext() && bounds2.hasNext()) {
      if (!isEqualTypes(bounds1.next(), bounds2.next())) {
        return false;
      }
    }
    return true;
  }

  static boolean isEquivalentTypes(Type type1, Type type2) {
    if (type1.isPrimitive() && type2.isPrimitive()) {
      PrimitiveType primitiveType1 = type1.asPrimitiveConfidently();
      PrimitiveType primitiveType2 = type2.asPrimitiveConfidently();
      return primitiveType1.typename().equals(primitiveType2.typename());
    } else if (type1.isCustom() && type2.isCustom()) {
      CustomStatement customStatement1 = type1.asCustomConfidently().statement();
      CustomStatement customStatement2 = type2.asCustomConfidently().statement();
      return customStatement1.canonicalName().equals(customStatement2.canonicalName());
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented");
    }
  }

  /**
   * Returns actual type declaration.
   */
  static String getActualTypeDeclaration(Type type) {
    return getActualTypeDeclaration(type, false);
  }

  static String getActualTypeDeclaration(
      Type type, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeDeclaration(type, false, simpleNameMapper);
  }

  static String getActualBlindTypeReferenceDeclaration(Type type) {
    return getActualTypeDeclaration(type, true);
  }

  static String getActualBlindTypeReferenceDeclaration(
      Type type, Function<String, String> simpleNameMapper
  ) {
    return getActualTypeDeclaration(type, true, simpleNameMapper);
  }

  private static String getActualTypeDeclaration(Type type, boolean blind) {
    return getActualTypeDeclaration(type, blind, tech.intellispaces.framework.commons.type.TypeFunctions::getSimpleName);
  }

  /**
   * Returns actual type declaration.
   */
  private static String getActualTypeDeclaration(
      Type type, boolean blind, Function<String, String> simpleNameMapper
  ) {
    if (type.asPrimitive().isPresent()) {
      return type.asPrimitive().get().typename();
    } else if (type.asArray().isPresent()) {
      Type elementType = type.asArray().get().elementType();
      return getActualTypeDeclaration(elementType, blind) + "[]";
    } else if (type.asCustom().isPresent()) {
      CustomStatement customStatement = type.asCustom().get().statement();
      String simpleName = simpleNameMapper.apply(customStatement.canonicalName());
      return simpleName + getTypeArgumentsDeclaration(type.asCustom().get(), blind, simpleNameMapper);
    } else if (type.asNamed().isPresent()) {
      return getNamedTypeReferenceDeclaration(type.asNamed().get(), blind, false);
    } else if (type.asWildcard().isPresent()) {
      return getWildcardDeclaration(type.asWildcard().get(), blind, true);
    } else {
      throw JavaStatementException.withMessage("Unsupported type {}", type.statementType().typename());
    }
  }

  static String getFormalFullTypeReferenceDeclaration(Type type) {
    return getFormalTypeReferenceDeclaration(type, true);
  }

  static String getFormalBriefTypeReferenceDeclaration(Type type) {
    return getFormalTypeReferenceDeclaration(type, false);
  }

  static String getFormalTypeReferenceDeclaration(Type type, boolean fullDeclaration) {
    if (type.asPrimitive().isPresent()) {
      return type.asPrimitive().get().typename();
    } else if (type.asArray().isPresent()) {
      Type elementType = type.asArray().get().elementType();
      return getFormalTypeReferenceDeclaration(elementType, fullDeclaration) + "[]";
    } else if (type.asCustom().isPresent()) {
      CustomStatement customStatement = type.asCustom().get().statement();
      return customStatement.simpleName() + CustomTypeFunctions.getTypeParametersDeclaration(customStatement, fullDeclaration);
    } else if (type.asNamed().isPresent()) {
      return getNamedTypeReferenceDeclaration(type.asNamed().get(), false, fullDeclaration);
    } else if (type.asWildcard().isPresent()) {
      return getWildcardDeclaration(type.asWildcard().get(), false, fullDeclaration);
    } else {
      throw JavaStatementException.withMessage("Unsupported type {}", type.statementType().typename());
    }
  }

  static String getTypeArgumentsDeclaration(CustomType typeReference) {
    return getTypeArgumentsDeclaration(typeReference, false, Function.identity());
  }

  static String getTypeArgumentsDeclaration(
      CustomType typeReference, Function<String, String> simpleNameMapper
  ) {
    return getTypeArgumentsDeclaration(typeReference, false, simpleNameMapper);
  }

  static String getTypeArgumentsDeclaration(
      CustomType typeReference, boolean blind, Function<String, String> simpleNameMapper
  ) {
    String arguments = typeReference.typeArguments().stream()
        .map(t -> getActualTypeDeclaration(t, blind, simpleNameMapper))
        .collect(Collectors.joining(", "));
    return (arguments.isEmpty() ? "" : "<" + arguments + ">");
  }

  static String getNamedTypeReferenceDeclaration(NamedType typeReference, boolean fullDeclaration) {
    return getNamedTypeReferenceDeclaration(typeReference, false, fullDeclaration);
  }

  static String getNamedTypeReferenceDeclaration(
      NamedType typeReference, boolean blind, boolean fullDeclaration
  ) {
    if (!fullDeclaration || typeReference.extendedBounds().isEmpty()) {
      return blind ? "?" : typeReference.name();
    } else {
      var sb = new StringBuilder();
      boolean first = true;
      for (TypeBound extendedTypeReference : typeReference.extendedBounds()) {
        if (!first) {
          sb.append(" & ");
        }
        first = false;
        sb.append(getActualTypeDeclaration(extendedTypeReference, blind));
      }
      return (blind ? "?" : typeReference.name()) + " extends " + sb;
    }
  }

  static String getWildcardDeclaration(WildcardType typeReference, boolean blind, boolean fullDeclaration) {
    if (!fullDeclaration) {
      return "?";
    } else {
      var sb = new StringBuilder();
      sb.append("?");
      if (typeReference.extendedBound().isPresent()) {
        sb.append(" extends ");
        sb.append(getActualTypeDeclaration(typeReference.extendedBound().get(), blind));
      }
      if (typeReference.superBound().isPresent()) {
        sb.append(" super ");
        sb.append(getActualTypeDeclaration(typeReference.superBound().get(), blind));
      }
      return sb.toString();
    }
  }

  static Map<String, NotPrimitiveType> getTypeArgumentMapping(CustomType customTypeReference) {
    List<NotPrimitiveType> typeArguments = customTypeReference.typeArguments();
    List<NamedType> typeParams = customTypeReference.statement().typeParameters();
    if (typeArguments.isEmpty() && typeParams.isEmpty()) {
      return Map.of();
    }
    if (typeArguments.size() != typeParams.size()) {
      throw UnexpectedViolationException.withMessage("Number of type arguments {} does not match with " +
          "number of type parameters {}. See type {}",
          typeArguments.size(), typeParams.size(), customTypeReference.formalFullDeclaration());
    }

    Map<String, NotPrimitiveType> mapping = new HashMap<>();
    Iterator<NotPrimitiveType> typeArgumentIterator = customTypeReference.typeArguments().iterator();
    Iterator<NamedType> typeParamIterator = customTypeReference.statement().typeParameters().iterator();
    while (typeArgumentIterator.hasNext()) {
      NotPrimitiveType typeArgument = typeArgumentIterator.next();
      NamedType typeParam = typeParamIterator.next();
      mapping.put(typeParam.name(), typeArgument);
    }
    return mapping;
  }
}
