package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;

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
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    TypeReference elementTypeReference = elementType().specify(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }
}
