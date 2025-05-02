package tech.intellispaces.statementsj.instance;

import java.util.List;

import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.reference.TypeReference;

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

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("vu9rJw");
  }
}
