package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.Map;

class ArrayTypeImpl extends AbstractType implements ArrayType {
  private final Type elementType;

  ArrayTypeImpl(Type elementType) {
    super();
    this.elementType = elementType;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayType;
  }

  @Override
  public Type elementType() {
    return elementType;
  }

  @Override
  public Type specify(Map<String, NonPrimitiveType> typeMapping) {
    Type elementType = elementType().specify(typeMapping);
    return new ArrayTypeImpl(elementType);
  }
}
