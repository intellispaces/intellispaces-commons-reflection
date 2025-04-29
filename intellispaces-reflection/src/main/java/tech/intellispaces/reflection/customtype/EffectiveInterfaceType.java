package tech.intellispaces.reflection.customtype;

import java.util.Map;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.reference.NotPrimitiveReference;

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
