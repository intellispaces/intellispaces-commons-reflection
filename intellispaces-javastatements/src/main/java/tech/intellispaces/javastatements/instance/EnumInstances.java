package tech.intellispaces.javastatements.instance;

import tech.intellispaces.javastatements.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
