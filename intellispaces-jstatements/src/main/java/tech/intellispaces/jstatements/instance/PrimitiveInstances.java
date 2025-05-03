package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
