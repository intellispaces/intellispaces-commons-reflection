package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.instance.EnumInstance;

public interface EnumInstanceBuilder {

  static EnumInstance build(EnumStatement type, String name) {
    return new EnumInstanceObject(type, name);
  }
}
