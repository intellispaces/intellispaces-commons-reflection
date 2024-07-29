package tech.intellispaces.javastatements.statement.reference;

import java.util.Optional;

/**
 * The array type reference.
 */
public interface ArrayReference extends NotPrimitiveTypeReference, TypeReferenceBound {

  /**
   * Elements type.
   */
  TypeReference elementType();

  @Override
  default boolean isArray() {
    return true;
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
    return false;
  }

  @Override
  default Optional<ArrayReference> asArray() {
    return Optional.of(this);
  }
}
