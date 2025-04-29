package tech.intellispaces.reflection.instance;

import java.util.Optional;

import tech.intellispaces.reflection.customtype.CustomType;

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
