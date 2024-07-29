package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.javastatements.statement.customtype.CustomType;

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
  default boolean isPrimitive() {
    return true;
  }

  @Override
  default boolean isArray() {
    return false;
  }

  @Override
  default boolean isCustomType() {
    return false;
  }

  @Override
  default boolean isNamed() {
    return false;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<PrimitiveReference> asPrimitive() {
    return Optional.of(this);
  }

  @Override
  default TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
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
