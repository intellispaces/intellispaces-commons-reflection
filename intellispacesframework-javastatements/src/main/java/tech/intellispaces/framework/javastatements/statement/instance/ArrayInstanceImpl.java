package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.util.List;

class ArrayInstanceImpl implements ArrayInstance {
  private final Type elementType;
  private final List<Instance> elements;

  ArrayInstanceImpl(Type elementType, List<Instance> elements) {
    this.elementType = elementType;
    this.elements = elements;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayInstance;
  }

  @Override
  public Type elementType() {
    return elementType;
  }

  @Override
  public List<Instance> elements() {
    return elements;
  }
}
