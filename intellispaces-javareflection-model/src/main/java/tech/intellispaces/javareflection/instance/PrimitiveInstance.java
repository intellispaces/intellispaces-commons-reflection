package tech.intellispaces.javareflection.instance;

import java.util.Optional;

import tech.intellispaces.javareflection.reference.PrimitiveReference;

/**
 * Primitive instance.
 */
public interface PrimitiveInstance extends Instance {

  @Override
  default Optional<PrimitiveInstance> asPrimitive() {
    return Optional.of(this);
  }

  /**
   * Type.
   */
  PrimitiveReference type();

  /**
   * Primitive value.
   */
  Object value();
}
