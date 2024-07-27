package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Primitive type.
 */
public interface PrimitiveType extends Type {

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
  default boolean isCustom() {
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
  default Optional<PrimitiveType> asPrimitive() {
    return Optional.of(this);
  }

  @Override
  default Type specify(Map<String, NonPrimitiveType> typeMapping) {
    return this;
  }

  @Override
  default Collection<CustomStatement> dependencies() {
    return List.of();
  }

  @Override
  default Collection<String> dependencyTypenames() {
    return List.of();
  }
}
