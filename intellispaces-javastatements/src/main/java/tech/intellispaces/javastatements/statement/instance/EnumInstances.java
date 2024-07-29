package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
