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
  default boolean isArray() {
    return false;
  }

  @Override
  default boolean isCustom() {
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
  default Optional<NamedType> asNamed() {
    return Optional.of(this);
  }
}
