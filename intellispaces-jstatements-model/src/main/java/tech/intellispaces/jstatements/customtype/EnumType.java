package tech.intellispaces.jstatements.customtype;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import tech.intellispaces.jstatements.reference.CustomTypeReference;
import tech.intellispaces.jstatements.reference.NamedReference;

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
  default Map<String, NamedReference> typeParameterMap() {
    return Map.of();
  }

  @Override
  default Optional<EnumType> asEnum() {
    return Optional.of(this);
  }

  default CustomType asCustomType() {
    return this;
  }
}
