package intellispaces.common.javastatement.reference;

import java.util.Optional;

/**
 * The wildcard type reference.
 */
public interface WildcardReference extends NotPrimitiveReference {

  /**
   * Extended bound.
   */
  Optional<ReferenceBound> extendedBound();

  /**
   * Super bound.
   */
  Optional<ReferenceBound> superBound();

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
    return true;
  }

  @Override
  default Optional<WildcardReference> asWildcard() {
    return Optional.of(this);
  }
}
