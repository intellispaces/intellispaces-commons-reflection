package tech.intellispaces.javareflection.instance;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.javareflection.reference.TypeReference;

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
