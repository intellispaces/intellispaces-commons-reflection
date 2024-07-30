package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.customtype.CustomType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Primitive type reference.
 */
public interface PrimitiveReference extends TypeReference {

  String typename();

  Class<?> wrapperClass();

  @Override
  default boolean isPrimitiveReference() {
    return true;
  }

  @Override
  default boolean isArrayReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return false;
  }

  @Override
  default boolean isNamedReference() {
    return false;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<PrimitiveReference> asPrimitiveReference() {
    return Optional.of(this);
  }

  @Override
  default TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
    return this;
  }

  @Override
  default Collection<CustomType> dependencies() {
    return List.of();
  }

  @Override
  default Collection<String> dependencyTypenames() {
    return List.of();
  }
}
