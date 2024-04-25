package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.custom.EnumStatement;

class EnumInstanceImpl implements EnumInstance {
  private final EnumStatement type;
  private final String name;

  EnumInstanceImpl(EnumStatement type, String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public StatementTypes statementType() {
    return StatementTypes.EnumInstance;
  }

  @Override
  public EnumStatement type() {
    return type;
  }

  @Override
  public String name() {
    return name;
  }
}
