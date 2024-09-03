package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
