package tech.intellispaces.statementsj.reference;

import java.util.Optional;

/**
 * The array type reference.
 */
public interface ArrayReference extends NotPrimitiveReference, ReferenceBound {

  /**
   * Elements type.
   */
  TypeReference elementType();

  @Override
  default boolean isArrayReference() {
    return true;
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
    return false;
  }

  @Override
  default Optional<ArrayReference> asArrayReference() {
    return Optional.of(this);
  }
}
