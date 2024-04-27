package tech.intellispacesframework.javastatements.statement.reference;

import java.util.Optional;

/**
 * The reference to non-primitive type.
 */
public interface NonPrimitiveTypeReference extends TypeReference {

  @Override
  default boolean isPrimitive() {
    return false;
  }

  @Override
  default Optional<NonPrimitiveTypeReference> asNonPrimitiveTypeReference() {
    return Optional.of(this);
  }
}
