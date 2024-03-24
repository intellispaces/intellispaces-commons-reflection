package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.PrimitiveInstance;
import intellispaces.javastatements.model.reference.PrimitiveTypeReference;

public interface PrimitiveInstanceBuilder {

  static PrimitiveInstance build(Object value, PrimitiveTypeReference type) {
    return new PrimitiveInstanceObject(value, type);
  }
}
