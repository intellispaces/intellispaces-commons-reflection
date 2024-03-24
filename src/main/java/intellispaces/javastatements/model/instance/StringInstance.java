package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Optional;

/**
 * String instance.
 */
public interface StringInstance extends Instance {

  @Override
  default StatementType statementType() {
    return StatementTypes.StringInstance;
  }

  @Override
  default Optional<StringInstance> asString() {
    return Optional.of(this);
  }

  /**
   * String value.
   */
  String value();
}
