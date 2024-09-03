package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.reference.PrimitiveReference;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
