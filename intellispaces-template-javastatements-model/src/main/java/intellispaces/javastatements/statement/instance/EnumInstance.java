package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.custom.EnumStatement;

import java.util.Optional;

/**
 * Enum instance.
 */
public interface EnumInstance extends Instance {

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
