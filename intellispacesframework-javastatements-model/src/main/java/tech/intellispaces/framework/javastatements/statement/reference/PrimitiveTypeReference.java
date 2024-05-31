package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The reference to primitive type.
 */
public interface PrimitiveTypeReference extends TypeReference {

  String typename();

  Class<?> wrapperClass();

  @Override
  default boolean isPrimitive() {
    return true;
  }

  @Override
  default Optional<PrimitiveTypeReference> asPrimitiveTypeReference() {
    return Optional.of(this);
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
