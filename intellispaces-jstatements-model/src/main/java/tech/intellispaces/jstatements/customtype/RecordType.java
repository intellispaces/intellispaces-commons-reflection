package tech.intellispaces.jstatements.customtype;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.jstatements.reference.CustomTypeReference;

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
