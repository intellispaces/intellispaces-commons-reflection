package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.Statement;

import java.util.List;
import java.util.Optional;

/**
 * Named type reference.
 *
 * <p>By default, named type is referred to {@link java.lang.Object}.
 */
public interface NamedReference extends NotPrimitiveTypeReference, TypeReferenceBound, ThrowableTypeReference {

  String name();

  List<TypeReferenceBound> extendedBounds();

  Statement owner();

  @Override
  default boolean isArray() {
    return false;
  }

  @Override
  default boolean isCustomType() {
    return false;
  }

  @Override
  default boolean isNamed() {
    return true;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<NamedReference> asNamed() {
    return Optional.of(this);
  }
}
