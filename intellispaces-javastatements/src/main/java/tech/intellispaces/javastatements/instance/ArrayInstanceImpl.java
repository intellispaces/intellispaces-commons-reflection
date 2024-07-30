package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

class ArrayInstanceImpl implements ArrayInstance {
  private final TypeReference elementTypeReference;
  private final List<Instance> elements;

  ArrayInstanceImpl(TypeReference elementTypeReference, List<Instance> elements) {
    this.elementTypeReference = elementTypeReference;
    this.elements = elements;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayInstance;
  }

  @Override
  public TypeReference elementType() {
    return elementTypeReference;
  }

  @Override
  public List<Instance> elements() {
    return elements;
  }
}
