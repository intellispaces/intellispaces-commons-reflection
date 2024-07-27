package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import java.util.List;
import java.util.Optional;

public interface InterfaceStatement extends CustomStatement {

  /**
   * Extended interfaces references.
   */
  default List<CustomType> extendedInterfaces() {
    return parentTypes();
  }

  @Override
  default Optional<InterfaceStatement> asInterface() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomStatement asCustomType() {
    return this;
  }
}
