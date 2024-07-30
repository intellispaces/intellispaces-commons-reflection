package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.reference.PrimitiveReference;

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
}
