package tech.intellispaces.commons.reflection.instance;

import tech.intellispaces.commons.reflection.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
