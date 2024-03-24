package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.ArrayInstance;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.TypeReference;

import java.util.List;

class ArrayInstanceObject implements ArrayInstance {
  private final TypeReference elementType;
  private final List<Instance> elements;

  ArrayInstanceObject(TypeReference elementType, List<Instance> elements) {
    this.elementType = elementType;
    this.elements = elements;
  }

  @Override
  public TypeReference elementType() {
    return elementType;
  }

  @Override
  public List<Instance> elements() {
    return elements;
  }
}
