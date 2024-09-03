package intellispaces.common.javastatements.reference;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;

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
  public TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference elementTypeReference = elementType().specify(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }
}
