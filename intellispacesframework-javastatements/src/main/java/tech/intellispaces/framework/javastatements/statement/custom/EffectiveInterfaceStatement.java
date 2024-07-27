package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

import java.util.Map;

class EffectiveInterfaceStatement extends AbstractEffectiveCustomStatement implements InterfaceStatement {

  EffectiveInterfaceStatement(
      InterfaceStatement actualType, Map<String, NotPrimitiveType> typeMapping
  ) {
    super(actualType, typeMapping);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
