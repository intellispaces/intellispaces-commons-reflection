package intellispaces.javastatements.function;

import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TypeReferenceFunctions {

  static Optional<TypeReference> narrowestOf(TypeReference typeReference1, TypeReference typeReference2) {
    if (typeReference1.asPrimitiveTypeReference().isPresent() && typeReference2.asPrimitiveTypeReference().isPresent()) {
      String type1 = typeReference1.asPrimitiveTypeReference().orElseThrow().typename();
      String type2 = typeReference2.asPrimitiveTypeReference().orElseThrow().typename();
      if (type1.equals(type2)) {
        return Optional.of(typeReference1);
      } else {
        return Optional.empty();
      }
    } else if (typeReference1.asNamedTypeReference().isPresent() && typeReference2.asNamedTypeReference().isPresent()) {
      NamedTypeReference type1 = typeReference1.asNamedTypeReference().orElseThrow();
      NamedTypeReference type2 = typeReference2.asNamedTypeReference().orElseThrow();
      if (type1.extendedBounds().isEmpty() && type2.extendedBounds().isEmpty()) {
        if (type1.name().equals(type2.name())) {
          return Optional.of(typeReference1);
        } else {
          return Optional.empty();
        }
      } else {
        throw new JavaStatementException("Not implemented yet");
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

  static boolean compareStrict(TypeReference typeReference1, TypeReference typeReference2) {
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
        throw new JavaStatementException("Not implemented yet");
      }
    } else {
      sameParams = false;
    }
    return sameParams;
  }
}
