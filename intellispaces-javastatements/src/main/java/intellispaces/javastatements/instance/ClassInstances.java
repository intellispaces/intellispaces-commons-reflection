package intellispaces.javastatements.instance;

import intellispaces.javastatements.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
