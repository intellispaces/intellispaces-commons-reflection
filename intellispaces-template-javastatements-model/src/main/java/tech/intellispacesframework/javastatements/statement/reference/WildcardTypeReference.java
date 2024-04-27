package tech.intellispacesframework.javastatements.statement.reference;

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
  default Optional<WildcardTypeReference> asWildcardTypeReference() {
    return Optional.of(this);
  }
}
