package tech.intellispacesframework.javastatements.statement.reference;

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
  default Optional<ArrayTypeReference> asArrayTypeReference() {
    return Optional.of(this);
  }
}
