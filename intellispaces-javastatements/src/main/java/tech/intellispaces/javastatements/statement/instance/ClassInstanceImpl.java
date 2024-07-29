package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.customtype.CustomType;

class ClassInstanceImpl implements ClassInstance {
  private final CustomType type;

  ClassInstanceImpl(CustomType type) {
    this.type = type;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ClassInstance;
  }

  @Override
  public CustomType type() {
    return type;
  }
}
