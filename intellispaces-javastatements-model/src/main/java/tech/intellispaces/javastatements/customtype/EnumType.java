package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.NamedReference;

import java.util.List;
import java.util.Optional;

/**
 * The enum type.
 */
public interface EnumType extends CustomType {

  /**
   * Implemented interfaces.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default List<NamedReference> typeParameters() {
    return List.of();
  }

  @Override
  default Optional<EnumType> asEnum() {
    return Optional.of(this);
  }

  default CustomType asCustomType() {
    return this;
  }
}
