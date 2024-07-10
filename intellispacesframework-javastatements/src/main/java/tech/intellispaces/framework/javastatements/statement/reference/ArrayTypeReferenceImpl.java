package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.Map;

class ArrayTypeReferenceImpl extends AbstractTypeReference implements ArrayTypeReference {
  private final TypeReference elementType;

  ArrayTypeReferenceImpl(TypeReference elementType) {
    super();
    this.elementType = elementType;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayTypeReference;
  }

  @Override
  public TypeReference elementType() {
    return elementType;
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeReference elementType = elementType().specify(typeMapping);
    return new ArrayTypeReferenceImpl(elementType);
  }
}
