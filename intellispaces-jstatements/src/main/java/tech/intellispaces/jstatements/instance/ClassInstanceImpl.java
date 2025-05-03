package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.CustomType;

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
