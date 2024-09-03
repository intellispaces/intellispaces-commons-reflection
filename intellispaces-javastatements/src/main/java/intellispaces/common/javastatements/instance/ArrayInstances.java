package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.reference.TypeReference;

import java.util.List;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementTypeReference, List<Instance> elements) {
    return new ArrayInstanceImpl(elementTypeReference, elements);
  }
}
