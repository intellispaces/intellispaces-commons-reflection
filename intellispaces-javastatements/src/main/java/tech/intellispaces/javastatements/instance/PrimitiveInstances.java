package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
