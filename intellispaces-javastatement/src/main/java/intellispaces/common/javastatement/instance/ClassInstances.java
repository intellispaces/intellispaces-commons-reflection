package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
