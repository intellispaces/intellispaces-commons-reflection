package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.reference.PrimitiveReference;

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
