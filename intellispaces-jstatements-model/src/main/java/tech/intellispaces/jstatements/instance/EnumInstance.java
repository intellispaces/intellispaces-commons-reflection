package tech.intellispaces.jstatements.instance;

import java.util.Optional;

import tech.intellispaces.jstatements.customtype.EnumType;

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
  EnumType type();

  /**
   * Enum name.
   */
  String name();
}
