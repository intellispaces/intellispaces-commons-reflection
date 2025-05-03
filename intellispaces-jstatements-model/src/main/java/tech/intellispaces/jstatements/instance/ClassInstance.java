package tech.intellispaces.jstatements.instance;

import java.util.Optional;

import tech.intellispaces.jstatements.customtype.CustomType;

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
