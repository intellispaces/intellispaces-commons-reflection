package tech.intellispaces.java.reflection.customtype;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;

import java.util.Map;

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
