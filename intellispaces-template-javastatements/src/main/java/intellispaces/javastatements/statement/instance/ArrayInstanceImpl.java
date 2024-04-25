package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.reference.TypeReference;

import java.util.List;

class ArrayInstanceImpl implements ArrayInstance {
  private final TypeReference elementType;
  private final List<Instance> elements;

  ArrayInstanceImpl(TypeReference elementType, List<Instance> elements) {
    this.elementType = elementType;
    this.elements = elements;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayInstance;
  }

  @Override
  public TypeReference elementType() {
    return elementType;
  }

  @Override
  public List<Instance> elements() {
    return elements;
  }
}
