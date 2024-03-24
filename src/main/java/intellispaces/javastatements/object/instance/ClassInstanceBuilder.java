package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.instance.ClassInstance;

public interface ClassInstanceBuilder {

  static ClassInstance build(CustomType type) {
    return new ClassInstanceObject(type);
  }
}
