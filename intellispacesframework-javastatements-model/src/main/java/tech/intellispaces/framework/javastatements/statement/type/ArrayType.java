package tech.intellispaces.framework.javastatements.statement.type;

import java.util.Optional;

/**
 * The array type.
 */
public interface ArrayType extends NonPrimitiveType, TypeBound {

  /**
   * Elements type.
   */
  Type elementType();

  @Override
  default boolean isArray() {
    return true;
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
  default Optional<ArrayType> asArray() {
    return Optional.of(this);
  }
}
