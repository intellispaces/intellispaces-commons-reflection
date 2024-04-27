package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.reference.PrimitiveTypeReference;

class PrimitiveInstanceImpl implements PrimitiveInstance {
  private final Object value;
  private final PrimitiveTypeReference type;

  PrimitiveInstanceImpl(Object value, PrimitiveTypeReference type) {
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
  public PrimitiveTypeReference type() {
    return type;
  }
}
