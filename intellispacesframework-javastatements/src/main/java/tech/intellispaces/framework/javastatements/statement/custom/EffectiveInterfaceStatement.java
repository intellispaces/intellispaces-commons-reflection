package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.type.NonPrimitiveType;

import java.util.Map;

class EffectiveInterfaceStatement extends AbstractEffectiveCustomStatement implements InterfaceStatement {

  EffectiveInterfaceStatement(
      InterfaceStatement actualType, Map<String, NonPrimitiveType> typeMapping
  ) {
    super(actualType, typeMapping);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
