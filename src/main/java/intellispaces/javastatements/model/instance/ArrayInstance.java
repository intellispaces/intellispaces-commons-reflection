package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * Array instance.
 */
public interface ArrayInstance extends Instance {

  @Override
  default StatementType statementType() {
    return StatementTypes.ArrayInstance;
  }

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
