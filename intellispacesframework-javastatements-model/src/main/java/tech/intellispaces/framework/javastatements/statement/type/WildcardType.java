package tech.intellispaces.framework.javastatements.statement.type;

import java.util.Optional;

/**
 * The wildcard type.
 */
public interface WildcardType extends NonPrimitiveType {

  /**
   * Extended bound.
   */
  Optional<TypeBound> extendedBound();

  /**
   * Super bound.
   */
  Optional<TypeBound> superBound();

  @Override
  default boolean isArrayType() {
    return false;
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
    return true;
  }

  @Override
  default Optional<WildcardType> asWildcardType() {
    return Optional.of(this);
  }
}
