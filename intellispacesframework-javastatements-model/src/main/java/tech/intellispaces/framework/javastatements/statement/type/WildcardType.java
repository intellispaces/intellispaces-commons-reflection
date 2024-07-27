package tech.intellispaces.framework.javastatements.statement.type;

import java.util.Optional;

/**
 * The wildcard type.
 */
public interface WildcardType extends NotPrimitiveType {

  /**
   * Extended bound.
   */
  Optional<TypeBound> extendedBound();

  /**
   * Super bound.
   */
  Optional<TypeBound> superBound();

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
    return true;
  }

  @Override
  default Optional<WildcardType> asWildcard() {
    return Optional.of(this);
  }
}
