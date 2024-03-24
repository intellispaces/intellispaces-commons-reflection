package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.instance.ClassInstance;

class ClassInstanceObject implements ClassInstance {
  private final CustomType type;

  ClassInstanceObject(CustomType type) {
    this.type = type;
  }

  @Override
  public CustomType type() {
    return type;
  }
}
