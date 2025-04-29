package tech.intellispaces.reflection.instance;

import java.util.List;

import tech.intellispaces.reflection.reference.TypeReference;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
