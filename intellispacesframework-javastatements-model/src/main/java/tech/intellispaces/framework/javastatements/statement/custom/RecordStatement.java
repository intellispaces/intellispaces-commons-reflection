package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import java.util.List;
import java.util.Optional;

public interface RecordStatement extends CustomStatement {

  /**
   * Implemented interfaces.
   */
  List<CustomType> implementedInterfaces();

  @Override
  default Optional<RecordStatement> asRecord() {
    return Optional.of(this);
  }

  /**
   * Related custom type.
   */
  default CustomStatement asCustomType() {
    return this;
  }
}
