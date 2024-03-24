package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

public interface RecordStatement extends CustomType {

  @Override
  default StatementType statementType() {
    return StatementTypes.Record;
  }

  /**
   * Implemented interfaces.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default Optional<RecordStatement> asRecord() {
    return Optional.of(this);
  }

  /**
   * Related custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
