package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
