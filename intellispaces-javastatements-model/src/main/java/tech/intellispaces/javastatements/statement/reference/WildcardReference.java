package tech.intellispaces.javastatements.statement.reference;

import java.util.Optional;

/**
 * The wildcard type reference.
 */
public interface WildcardReference extends NotPrimitiveTypeReference {

  /**
   * Extended bound.
   */
  Optional<TypeReferenceBound> extendedBound();

  /**
   * Super bound.
   */
  Optional<TypeReferenceBound> superBound();

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
    return true;
  }

  @Override
  default Optional<WildcardReference> asWildcard() {
    return Optional.of(this);
  }
}
