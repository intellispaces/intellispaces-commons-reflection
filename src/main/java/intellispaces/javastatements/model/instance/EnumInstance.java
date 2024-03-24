package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Optional;

/**
 * Enum instance.
 */
public interface EnumInstance extends Instance {

  @Override
  default StatementTypes statementType() {
    return StatementTypes.EnumInstance;
  }

  @Override
  default Optional<EnumInstance> asEnum() {
    return Optional.of(this);
  }

  /**
   * Enum type statement.
   */
  EnumStatement type();

  /**
   * Enum name.
   */
  String name();
}
