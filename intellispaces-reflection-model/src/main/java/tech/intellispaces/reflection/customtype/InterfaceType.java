package tech.intellispaces.reflection.customtype;

import tech.intellispaces.reflection.reference.CustomTypeReference;

import java.util.List;
import java.util.Optional;

/**
 * The interface type.
 */
public interface InterfaceType extends CustomType {

  /**
   * Extended interfaces references.
   */
  default List<CustomTypeReference> extendedInterfaces() {
    return parentTypes();
  }

  @Override
  default Optional<InterfaceType> asInterface() {
    return Optional.of(this);
  }

  /**
   * Connected custom type.
   */
  default CustomType asCustomType() {
    return this;
  }
}
