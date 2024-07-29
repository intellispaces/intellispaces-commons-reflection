package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.List;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
