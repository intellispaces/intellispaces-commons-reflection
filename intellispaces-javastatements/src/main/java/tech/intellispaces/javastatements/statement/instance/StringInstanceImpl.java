package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;

class StringInstanceImpl implements StringInstance {
  private final String string;

  StringInstanceImpl(String string) {
    this.string = string;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.StringInstance;
  }

  @Override
  public String value() {
    return string;
  }
}
