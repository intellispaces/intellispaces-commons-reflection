package tech.intellispaces.statementsj.reference;

import java.util.Map;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;

/**
 * Adapter of {@link javax.lang.model.type.ArrayType} to {@link ArrayReference}.
 */
class ArrayReferenceBasedOnElementClass extends AbstractTypeReference implements ArrayReference {
  private final Class<?> elementClass;

  ArrayReferenceBasedOnElementClass(Class<?> elementClass) {
    super();
    this.elementClass = elementClass;
  }

  @Override
  public boolean isVoidType() {
    return false;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayReference;
  }

  @Override
  public TypeReference elementType() {
    return TypeReferences.of(elementClass);
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference elementTypeReference = elementType().effective(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }
}
