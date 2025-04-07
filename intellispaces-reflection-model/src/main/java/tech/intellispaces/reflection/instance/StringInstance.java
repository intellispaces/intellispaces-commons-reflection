package tech.intellispaces.reflection.instance;

import java.util.Optional;

/**
 * String instance.
 */
public interface StringInstance extends Instance {

  @Override
  default Optional<StringInstance> asString() {
    return Optional.of(this);
  }

  /**
   * String value.
   */
  String value();
}
