package tech.intellispaces.framework.javastatements.statement.reference;

import java.util.Optional;

/**
 * The array type reference.
 */
public interface ArrayTypeReference extends NonPrimitiveTypeReference, TypeBoundReference {

  /**
   * Array elements type.
   */
  TypeReference elementType();

  @Override
  default boolean isArrayTypeReference() {
    return true;
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
    return false;
  }

  @Override
  default Optional<ArrayTypeReference> asArrayTypeReference() {
    return Optional.of(this);
  }
}
