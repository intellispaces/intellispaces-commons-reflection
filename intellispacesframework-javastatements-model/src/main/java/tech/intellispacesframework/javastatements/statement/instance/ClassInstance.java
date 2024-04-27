package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.statement.custom.CustomType;

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
