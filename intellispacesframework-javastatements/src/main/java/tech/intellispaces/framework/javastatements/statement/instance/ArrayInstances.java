package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.util.List;

public interface ArrayInstances {

  static ArrayInstance of(Type elementType, List<Instance> elements) {
    return new ArrayInstanceImpl(elementType, elements);
  }
}
