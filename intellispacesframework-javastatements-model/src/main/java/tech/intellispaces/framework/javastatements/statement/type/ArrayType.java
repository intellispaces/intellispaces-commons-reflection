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
  default boolean isArrayType() {
    return true;
  }

  @Override
  default boolean isCustomType() {
    return false;
  }

  @Override
  default boolean isNamedType() {
    return false;
  }

  @Override
  default boolean isWildcardType() {
    return false;
  }

  @Override
  default Optional<ArrayType> asArrayType() {
    return Optional.of(this);
  }
}
