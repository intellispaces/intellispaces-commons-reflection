package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * The enum.
 */
public interface EnumStatement extends CustomType {

  @Override
  default StatementType statementType() {
    return StatementTypes.Enum;
  }

  /**
   * Implemented interfaces.
   */
  List<CustomTypeReference> implementedInterfaces();

  @Override
  default List<NamedTypeReference> typeParameters() {
    return List.of();
  }

  @Override
  default Optional<EnumStatement> asEnum() {
    return Optional.of(this);
  }

  default CustomType asCustomType() {
    return this;
  }
}
