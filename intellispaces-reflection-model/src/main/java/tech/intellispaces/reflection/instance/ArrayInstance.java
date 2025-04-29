package tech.intellispaces.reflection.instance;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.reflection.reference.TypeReference;

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
