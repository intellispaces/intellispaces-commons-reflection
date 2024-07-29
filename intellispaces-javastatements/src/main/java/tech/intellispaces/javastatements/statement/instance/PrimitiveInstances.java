package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
