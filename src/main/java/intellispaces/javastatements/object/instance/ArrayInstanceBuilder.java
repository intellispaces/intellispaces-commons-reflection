package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.ArrayInstance;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.TypeReference;

import java.util.List;

public interface ArrayInstanceBuilder {

  static ArrayInstance build(TypeReference elementType, List<Instance> elements) {
    return new ArrayInstanceObject(elementType, elements);
  }
}
