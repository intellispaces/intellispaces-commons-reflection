package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.reference.TypeReference;

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
   * Element type.
   */
  TypeReference elementType();

  /**
   * Elements.
   */
  List<Instance> elements();
}
