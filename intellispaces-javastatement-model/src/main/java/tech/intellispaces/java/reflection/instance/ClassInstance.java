package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.customtype.CustomType;

import java.util.Optional;

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
