package tech.intellispaces.reflection.instance;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.reference.PrimitiveReference;

class PrimitiveInstanceImpl implements PrimitiveInstance {
  private final Object value;
  private final PrimitiveReference type;

  PrimitiveInstanceImpl(Object value, PrimitiveReference type) {
    this.value = value;
    this.type = type;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.PrimitiveInstance;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public PrimitiveReference type() {
    return type;
  }

  @Override
  public String prettyDeclaration() {
    return value.toString();
  }
}
