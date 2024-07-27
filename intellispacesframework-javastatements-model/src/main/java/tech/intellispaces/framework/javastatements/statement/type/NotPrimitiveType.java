package tech.intellispaces.framework.javastatements.statement.type;

import java.util.Optional;

/**
 * Not-primitive type.
 */
public interface NotPrimitiveType extends Type {

  @Override
  default boolean isPrimitive() {
    return false;
  }

  @Override
  default Optional<NotPrimitiveType> asNotPrimitive() {
    return Optional.of(this);
  }
}
