package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;

class MethodStatementImpl implements MethodStatement {
  private final CustomType holder;
  private final MethodSignature signature;

  MethodStatementImpl(CustomType holder, MethodSignature signature) {
    this.holder = holder;
    this.signature = signature;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType holder() {
    return holder;
  }

  @Override
  public MethodSignature signature() {
    return signature;
  }
}
