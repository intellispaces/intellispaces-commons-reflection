package tech.intellispaces.statementsj.customtype;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.statementsj.method.MethodStatement;
import tech.intellispaces.statementsj.reference.CustomTypeReference;

/**
 * The class type.
 */
public interface ClassType extends CustomType {

  List<MethodStatement> constructors();

  /**
   * Extended class references.
   */
  Optional<CustomTypeReference> extendedClass();

  /**
   * Implemented interface references.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default Optional<ClassType> asClass() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
