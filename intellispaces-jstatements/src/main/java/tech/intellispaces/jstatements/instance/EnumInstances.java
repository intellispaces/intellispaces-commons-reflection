package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
