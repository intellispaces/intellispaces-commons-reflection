package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

public interface ClassStatement extends CustomType {

  @Override
  default StatementType statementType() {
    return StatementTypes.Class;
  }

  /**
   * Extended class references.
   */
  Optional<CustomTypeReference> extendedClass();

  /**
   * Implemented interface references.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default Optional<ClassStatement> asClass() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
