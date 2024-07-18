package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.Map;

class EffectiveInterfaceStatement extends AbstractEffectiveCustomType implements InterfaceStatement {

  public EffectiveInterfaceStatement(InterfaceStatement actualType, Map<String, NonPrimitiveTypeReference> typeMapping) {
    super(actualType, typeMapping);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
