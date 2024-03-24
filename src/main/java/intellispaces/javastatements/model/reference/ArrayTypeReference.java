package intellispaces.javastatements.model.reference;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.object.StatementTypes;

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
  default StatementType statementType() {
    return StatementTypes.ArrayTypeReference;
  }

  @Override
  default Optional<ArrayTypeReference> asArrayTypeReference() {
    return Optional.of(this);
  }
}
