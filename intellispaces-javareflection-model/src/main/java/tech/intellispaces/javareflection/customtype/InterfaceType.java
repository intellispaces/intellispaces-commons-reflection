package tech.intellispaces.javareflection.customtype;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.javareflection.reference.CustomTypeReference;

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
