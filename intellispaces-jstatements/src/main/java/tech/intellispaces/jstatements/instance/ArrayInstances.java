package tech.intellispaces.jstatements.instance;

import java.util.List;

import tech.intellispaces.jstatements.reference.TypeReference;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
