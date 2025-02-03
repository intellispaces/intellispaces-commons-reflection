package tech.intellispaces.commons.java.reflection.instance;

import tech.intellispaces.commons.java.reflection.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
