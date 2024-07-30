package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
