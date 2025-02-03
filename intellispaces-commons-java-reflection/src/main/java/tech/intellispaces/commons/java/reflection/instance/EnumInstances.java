package tech.intellispaces.commons.java.reflection.instance;

import tech.intellispaces.commons.java.reflection.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
