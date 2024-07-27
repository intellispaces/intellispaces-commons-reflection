package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.type.PrimitiveType;

public interface PrimitiveInstances {

  static PrimitiveInstance of(Object value, PrimitiveType type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
