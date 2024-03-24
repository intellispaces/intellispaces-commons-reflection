package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.instance.EnumInstance;

class EnumInstanceObject implements EnumInstance {
  private final EnumStatement type;
  private final String name;

  EnumInstanceObject(EnumStatement type, String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public EnumStatement type() {
    return type;
  }

  @Override
  public String name() {
    return name;
  }
}
