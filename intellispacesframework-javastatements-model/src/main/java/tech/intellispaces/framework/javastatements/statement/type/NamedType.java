package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.Statement;

import java.util.List;
import java.util.Optional;

/**
 * Named type.
 *
 * <p>By default, named type is referred to {@link java.lang.Object}.
 */
public interface NamedType extends NonPrimitiveType, TypeBound, ExceptionCompatibleType {

  String name();

  List<TypeBound> extendedBounds();

  Statement owner();

  @Override
  default boolean isArrayType() {
    return false;
  }

  @Override
  default boolean isCustomType() {
    return false;
  }

  @Override
  default boolean isNamedType() {
    return true;
  }

  @Override
  default boolean isWildcardType() {
    return false;
  }

  @Override
  default Optional<NamedType> asNamedType() {
    return Optional.of(this);
  }
}
