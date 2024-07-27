package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import java.util.List;
import java.util.Optional;

public interface ClassStatement extends CustomStatement {

  List<MethodStatement> constructors();

  /**
   * Extended class references.
   */
  Optional<CustomType> extendedClass();

  /**
   * Implemented interface references.
   */
  List<CustomType> implementedInterfaces();

  @Override
  default Optional<ClassStatement> asClass() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomStatement asCustomType() {
    return this;
  }
}
