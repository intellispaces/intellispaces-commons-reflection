package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
