package tech.intellispaces.javareflection.instance;

import java.util.List;

import tech.intellispaces.javareflection.reference.TypeReference;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
