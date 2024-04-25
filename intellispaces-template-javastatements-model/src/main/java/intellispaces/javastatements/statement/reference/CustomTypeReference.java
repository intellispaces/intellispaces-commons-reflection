package intellispaces.javastatements.statement.reference;

import intellispaces.javastatements.statement.custom.CustomType;

import java.util.List;
import java.util.Optional;

/**
 * The custom type reference.
 */
public interface CustomTypeReference extends NonPrimitiveTypeReference, TypeBoundReference, ExceptionCompatibleTypeReference {

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
