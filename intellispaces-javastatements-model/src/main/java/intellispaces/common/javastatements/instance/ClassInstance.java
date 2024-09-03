package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.customtype.CustomType;

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
