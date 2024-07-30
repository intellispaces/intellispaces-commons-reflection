package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;

import java.util.Map;

class EffectiveInterfaceType extends AbstractEffectiveCustomType implements InterfaceType {

  EffectiveInterfaceType(
      InterfaceType actualType, Map<String, NotPrimitiveTypeReference> typeMapping
  ) {
    super(actualType, typeMapping);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
