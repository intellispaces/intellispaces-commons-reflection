package tech.intellispaces.statementsj.instance;

import java.util.Optional;

import tech.intellispaces.statementsj.customtype.CustomType;

/**
 * Class instance.
 */
public interface ClassInstance extends Instance {

  @Override
  default Optional<ClassInstance> asClass() {
    return Optional.of(this);
  }

  /**
   * Class type statement.
   */
  CustomType type();
}
