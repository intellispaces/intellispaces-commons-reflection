package tech.intellispaces.reflection.instance;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;

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
