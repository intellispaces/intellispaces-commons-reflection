package tech.intellispaces.commons.reflection.instance;

import tech.intellispaces.commons.reflection.reference.TypeReference;

import java.util.List;
import java.util.Optional;

/**
 * Array instance.
 */
public interface ArrayInstance extends Instance {

  @Override
  default Optional<ArrayInstance> asArray() {
    return Optional.of(this);
  }

  /**
   * Element type reference.
   */
  TypeReference elementType();

  /**
   * Elements.
   */
  List<Instance> elements();
}
