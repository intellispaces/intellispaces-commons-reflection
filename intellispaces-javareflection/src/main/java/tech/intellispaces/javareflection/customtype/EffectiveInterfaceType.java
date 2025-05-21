package tech.intellispaces.javareflection.customtype;

import tech.intellispaces.javareflection.StatementType;
import tech.intellispaces.javareflection.StatementTypes;
import tech.intellispaces.javareflection.reference.NotPrimitiveReference;

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
