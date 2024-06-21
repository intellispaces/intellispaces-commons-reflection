package tech.intellispaces.framework.javastatements.statement.reference;

import java.util.Optional;

/**
 * The wildcard type reference.
 */
public interface WildcardTypeReference extends NonPrimitiveTypeReference {

  /**
   * Extended bound.
   */
  Optional<TypeBoundReference> extendedBound();

  /**
   * Super bound.
   */
  Optional<TypeBoundReference> superBound();

  @Override
  default boolean isArrayTypeReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return false;
  }

  @Override
  default boolean isNamedTypeReference() {
    return false;
  }

  @Override
  default boolean isWildcardTypeReference() {
    return true;
  }

  @Override
  default Optional<WildcardTypeReference> asWildcardTypeReference() {
    return Optional.of(this);
  }
}
