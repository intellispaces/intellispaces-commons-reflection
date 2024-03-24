package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Optional;

/**
 * Class instance.
 */
public interface ClassInstance extends Instance {

  @Override
  default StatementType statementType() {
    return StatementTypes.ClassInstance;
  }

  @Override
  default Optional<ClassInstance> asClass() {
    return Optional.of(this);
  }

  /**
   * Class type statement.
   */
  CustomType type();
}
