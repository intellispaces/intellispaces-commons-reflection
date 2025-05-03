package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.EnumType;

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

  @Override
  public String prettyDeclaration() {
    return type.canonicalName() + "." + name;
  }
}
