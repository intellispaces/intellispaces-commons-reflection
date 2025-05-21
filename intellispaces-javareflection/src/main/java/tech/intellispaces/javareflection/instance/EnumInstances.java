package tech.intellispaces.javareflection.instance;

import tech.intellispaces.javareflection.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
