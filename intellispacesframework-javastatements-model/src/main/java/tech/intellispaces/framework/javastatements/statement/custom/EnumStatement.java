package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;

import java.util.List;
import java.util.Optional;

/**
 * The enum.
 */
public interface EnumStatement extends CustomStatement {

  /**
   * Implemented interfaces.
   */
  List<CustomType> implementedInterfaces();

  @Override
  default List<NamedType> typeParameters() {
    return List.of();
  }

  @Override
  default Optional<EnumStatement> asEnum() {
    return Optional.of(this);
  }

  default CustomStatement asCustomType() {
    return this;
  }
}
