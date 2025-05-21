package tech.intellispaces.javareflection.instance;

import java.util.Optional;

import tech.intellispaces.javareflection.customtype.CustomType;

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
