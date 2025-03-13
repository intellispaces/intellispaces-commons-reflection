package tech.intellispaces.commons.reflection.reference;

import java.util.Optional;

/**
 * Not-primitive type reference.
 */
public interface NotPrimitiveReference extends TypeReference {

  @Override
  default boolean isPrimitiveReference() {
    return false;
  }

  @Override
  default Optional<NotPrimitiveReference> asNotPrimitiveReference() {
    return Optional.of(this);
  }
}
