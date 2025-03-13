package tech.intellispaces.commons.reflection.instance;

import tech.intellispaces.commons.reflection.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
