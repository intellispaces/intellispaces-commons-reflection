package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.reference.NotPrimitiveReference;

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
