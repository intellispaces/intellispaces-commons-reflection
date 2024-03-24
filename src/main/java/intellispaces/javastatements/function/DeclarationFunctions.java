package intellispaces.javastatements.function;

import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeBoundReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.reference.WildcardTypeReference;

import java.util.stream.Collectors;

public interface DeclarationFunctions {

  /**
   * Returns type reference declaration.
   */
  static String getReferenceDeclaration(TypeReference typeReference) {
    if (typeReference.asPrimitiveTypeReference().isPresent()) {
      return typeReference.asPrimitiveTypeReference().get().typename();
    } else if (typeReference.asArrayTypeReference().isPresent()) {
      TypeReference elementType = typeReference.asArrayTypeReference().get().elementType();
      return getReferenceDeclaration(elementType) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      return customType.simpleName() + getTypeArgumentsDeclaration(typeReference.asCustomTypeReference().get());
    } else if (typeReference.asNamedTypeReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedTypeReference().get(), false);
    } else if (typeReference.asWildcardTypeReference().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcardTypeReference().get(), true);
    } else {
      throw new JavaStatementException("Unsupported reference type {}", typeReference.statementType().typename());
    }
  }

  static String getTypeFullDeclaration(TypeReference typeReference) {
    return getTypeDeclaration(typeReference, true);
  }

  static String getTypeBriefDeclaration(TypeReference typeReference) {
    return getTypeDeclaration(typeReference, false);
  }

  private static String getTypeDeclaration(TypeReference typeReference, boolean expanded) {
    if (typeReference.asPrimitiveTypeReference().isPresent()) {
      return typeReference.asPrimitiveTypeReference().get().typename();
    } else if (typeReference.asArrayTypeReference().isPresent()) {
      TypeReference elementType = typeReference.asArrayTypeReference().get().elementType();
      return getTypeDeclaration(elementType, expanded) + "[]";
    } else if (typeReference.asCustomTypeReference().isPresent()) {
      CustomType customType = typeReference.asCustomTypeReference().get().targetType();
      return customType.simpleName() + getTypeParametersDeclaration(customType, expanded);
    } else if (typeReference.asNamedTypeReference().isPresent()) {
      return getNamedTypeReferenceDeclaration(typeReference.asNamedTypeReference().get(), expanded);
    } else if (typeReference.asWildcardTypeReference().isPresent()) {
      return getWildcardDeclaration(typeReference.asWildcardTypeReference().get(), expanded);
    } else {
      throw new JavaStatementException("Unsupported reference type {}", typeReference.statementType().typename());
    }
  }

  static String getTypeArgumentsDeclaration(CustomTypeReference typeReference) {
    String arguments = typeReference.typeArguments().stream()
        .map(DeclarationFunctions::getReferenceDeclaration)
        .collect(Collectors.joining(", "));
    return (arguments.isEmpty() ? "" : "<" + arguments + ">");
  }

  private static String getTypeParametersDeclaration(CustomType customType, boolean expanded) {
    var parametersSource = customType.typeParameters().stream()
        .map(param -> getNamedTypeReferenceDeclaration(param, expanded))
        .collect(Collectors.joining(", "));
    return (parametersSource.isEmpty() ? "" : "<" + parametersSource + ">");
  }

  private static String getNamedTypeReferenceDeclaration(NamedTypeReference typeReference, boolean expanded) {
    if (!expanded || typeReference.extendedBounds().isEmpty()) {
      return typeReference.name();
    } else {
      var sb = new StringBuilder();
      boolean first = true;
      for (TypeBoundReference extendedTypeReference : typeReference.extendedBounds()) {
        if (!first) {
          sb.append(" & ");
        }
        first = false;
        sb.append(getReferenceDeclaration(extendedTypeReference));
      }
      return typeReference.name() + " extends " + sb;
    }
  }

  private static String getWildcardDeclaration(WildcardTypeReference typeReference, boolean expanded) {
    if (!expanded) {
      return "?";
    } else {
      var sb = new StringBuilder();
      sb.append("?");
      if (typeReference.extendedBound().isPresent()) {
        sb.append(" extends ");
        sb.append(getReferenceDeclaration(typeReference.extendedBound().get()));
      }
      if (typeReference.superBound().isPresent()) {
        sb.append(" super ");
        sb.append(getReferenceDeclaration(typeReference.superBound().get()));
      }
      return sb.toString();
    }
  }
}
