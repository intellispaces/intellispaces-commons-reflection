package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.reference.PrimitiveTypeReference;
import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Optional;

/**
 * Primitive instance.
 */
public interface PrimitiveInstance extends Instance {

  @Override
  default StatementType statementType() {
    return StatementTypes.PrimitiveInstance;
  }

  @Override
  default Optional<PrimitiveInstance> asPrimitive() {
    return Optional.of(this);
  }

  /**
   * Primitive type reference.
   */
  PrimitiveTypeReference type();

  /**
   * Primitive value.
   */
  Object value();
}
