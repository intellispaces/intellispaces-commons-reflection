package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.reference.PrimitiveTypeReference;

public interface PrimitiveInstanceBuilder {

  static PrimitiveInstance build(Object value, PrimitiveTypeReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
