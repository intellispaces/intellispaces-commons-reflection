package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.reference.PrimitiveReference;

import java.util.Optional;

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
