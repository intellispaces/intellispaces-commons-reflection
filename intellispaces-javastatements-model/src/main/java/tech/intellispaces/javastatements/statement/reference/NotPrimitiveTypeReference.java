package tech.intellispaces.javastatements.statement.reference;

import java.util.Optional;

/**
 * Not-primitive type reference.
 */
public interface NotPrimitiveTypeReference extends TypeReference {

  @Override
  default boolean isPrimitive() {
    return false;
  }

  @Override
  default Optional<NotPrimitiveTypeReference> asNotPrimitive() {
    return Optional.of(this);
  }
}
