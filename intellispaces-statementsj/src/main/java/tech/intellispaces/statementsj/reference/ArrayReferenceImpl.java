package tech.intellispaces.statementsj.reference;

import java.util.Map;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;

class ArrayReferenceImpl extends AbstractTypeReference implements ArrayReference {
  private final TypeReference elementTypeReference;

  ArrayReferenceImpl(TypeReference elementTypeReference) {
    super();
    this.elementTypeReference = elementTypeReference;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayReference;
  }

  @Override
  public TypeReference elementType() {
    return elementTypeReference;
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference elementTypeReference = elementType().effective(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }

  @Override
  public boolean isVoidType() {
    return false;
  }
}
