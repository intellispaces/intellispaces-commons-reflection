package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;

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
