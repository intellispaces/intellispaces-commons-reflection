package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.EnumType;

class EnumInstanceImpl implements EnumInstance {
  private final EnumType type;
  private final String name;

  EnumInstanceImpl(EnumType type, String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public StatementTypes statementType() {
    return StatementTypes.EnumInstance;
  }

  @Override
  public EnumType type() {
    return type;
  }

  @Override
  public String name() {
    return name;
  }
}
