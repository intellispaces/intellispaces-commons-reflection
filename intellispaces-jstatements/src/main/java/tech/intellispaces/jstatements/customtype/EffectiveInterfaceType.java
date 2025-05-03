package tech.intellispaces.jstatements.customtype;

import java.util.Map;

import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;

class EffectiveInterfaceType extends AbstractEffectiveCustomType implements InterfaceType {

  EffectiveInterfaceType(
      InterfaceType actualType, Map<String, NotPrimitiveReference> typeMapping
  ) {
    super(actualType, typeMapping);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
