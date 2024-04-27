package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;

import java.util.List;
import java.util.Optional;

public interface ClassStatement extends CustomType {

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
