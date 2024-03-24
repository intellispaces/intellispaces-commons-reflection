package intellispaces.javastatements.model.reference;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * The custom type reference.
 */
public interface CustomTypeReference extends NonPrimitiveTypeReference, TypeBoundReference, ExceptionCompatibleTypeReference {

  @Override
  default StatementType statementType() {
    return StatementTypes.CustomTypeReference;
  }

  /**
   * Target type.
   */
  CustomType targetType();

  /**
   * Actual arguments of type parameters.
   */
  List<NonPrimitiveTypeReference> typeArguments();

  @Override
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.of(this);
  }
}
