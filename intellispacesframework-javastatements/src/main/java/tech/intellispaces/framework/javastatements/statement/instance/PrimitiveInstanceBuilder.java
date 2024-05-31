package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.reference.PrimitiveTypeReference;

public interface PrimitiveInstanceBuilder {

  static PrimitiveInstance build(Object value, PrimitiveTypeReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
