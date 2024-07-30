package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.CustomType;

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
