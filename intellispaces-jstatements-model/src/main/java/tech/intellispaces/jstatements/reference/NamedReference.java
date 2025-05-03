package tech.intellispaces.jstatements.reference;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.jstatements.Statement;

/**
 * Named type reference.
 *
 * <p>By default, named type is referred to {@link java.lang.Object}.
 */
public interface NamedReference extends NotPrimitiveReference, ReferenceBound, ThrowableReference {

  String name();

  List<ReferenceBound> extendedBounds();

  Statement owner();

  @Override
  default boolean isArrayReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return false;
  }

  @Override
  default boolean isNamedReference() {
    return true;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<NamedReference> asNamedReference() {
    return Optional.of(this);
  }
}
