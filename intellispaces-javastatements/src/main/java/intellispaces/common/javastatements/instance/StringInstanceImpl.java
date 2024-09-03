package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;

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
