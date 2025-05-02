package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;

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

  @Override
  public String prettyDeclaration() {
    return "\"" + string + "\"";
  }
}
