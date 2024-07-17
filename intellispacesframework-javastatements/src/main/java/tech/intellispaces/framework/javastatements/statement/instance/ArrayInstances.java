package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.util.List;

public interface ArrayInstances {

  static ArrayInstance of(TypeReference elementType, List<Instance> elements) {
    return new ArrayInstanceImpl(elementType, elements);
  }
}
