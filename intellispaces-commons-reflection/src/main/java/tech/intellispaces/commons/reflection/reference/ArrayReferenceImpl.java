package tech.intellispaces.commons.reflection.reference;

import tech.intellispaces.commons.reflection.StatementType;
import tech.intellispaces.commons.reflection.StatementTypes;

import java.util.Map;

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
