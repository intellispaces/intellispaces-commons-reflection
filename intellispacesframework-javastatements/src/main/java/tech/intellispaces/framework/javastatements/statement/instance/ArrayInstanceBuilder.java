package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.util.List;

public interface ArrayInstanceBuilder {

  static ArrayInstance build(TypeReference elementType, List<Instance> elements) {
    return new ArrayInstanceImpl(elementType, elements);
  }
}
