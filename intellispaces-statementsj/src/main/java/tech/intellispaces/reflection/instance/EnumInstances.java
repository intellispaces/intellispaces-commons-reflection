package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
