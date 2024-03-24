package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.PrimitiveInstance;
import intellispaces.javastatements.model.reference.PrimitiveTypeReference;

class PrimitiveInstanceObject implements PrimitiveInstance {
  private final Object value;
  private final PrimitiveTypeReference type;

  PrimitiveInstanceObject(Object value, PrimitiveTypeReference type) {
    this.value = value;
    this.type = type;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public PrimitiveTypeReference type() {
    return type;
  }
}
