package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.customtype.CustomType;

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

  @Override
  public String prettyDeclaration() {
    return type.canonicalName() + ".class";
  }
}
