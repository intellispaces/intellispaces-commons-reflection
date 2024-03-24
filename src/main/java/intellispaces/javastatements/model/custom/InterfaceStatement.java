package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

public interface InterfaceStatement extends CustomType {

  @Override
  default StatementType statementType() {
    return StatementTypes.Interface;
  }

  /**
   * Extended interfaces references.
   */
  default List<CustomTypeReference> extendedInterfaces() {
    return parentTypes();
  }

  @Override
  default Optional<InterfaceStatement> asInterface() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
