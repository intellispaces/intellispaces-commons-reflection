package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.Statement;

import java.util.List;
import java.util.Optional;

/**
 * Named type reference.
 *
 * <p>By default, named type reference is referred to {@link java.lang.Object}.
 */
public interface NamedTypeReference extends NonPrimitiveTypeReference, TypeBoundReference, ExceptionCompatibleTypeReference {

  String name();

  List<TypeBoundReference> extendedBounds();

  Statement owner();

  @Override
  default boolean isArrayTypeReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return false;
  }

  @Override
  default boolean isNamedTypeReference() {
    return true;
  }

  @Override
  default boolean isWildcardTypeReference() {
    return false;
  }

  @Override
  default Optional<NamedTypeReference> asNamedTypeReference() {
    return Optional.of(this);
  }
}
