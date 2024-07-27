package tech.intellispaces.framework.javastatements.statement.type;

import java.util.Optional;

/**
 * Non-primitive type.
 */
public interface NonPrimitiveType extends Type {

  @Override
  default boolean isPrimitive() {
    return false;
  }

  @Override
  default Optional<NonPrimitiveType> asNonPrimitiveType() {
    return Optional.of(this);
  }
}
