package tech.intellispaces.reflection.customtype;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.reflection.reference.CustomTypeReference;

/**
 * The record type.
 */
public interface RecordType extends CustomType {

  /**
   * Implemented interfaces.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default Optional<RecordType> asRecord() {
    return Optional.of(this);
  }

  /**
   * Related custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
