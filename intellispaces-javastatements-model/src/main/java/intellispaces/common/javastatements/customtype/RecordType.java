package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.reference.CustomTypeReference;

import java.util.List;
import java.util.Optional;

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
