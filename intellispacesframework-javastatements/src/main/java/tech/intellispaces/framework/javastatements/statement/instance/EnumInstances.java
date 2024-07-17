package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;

public interface EnumInstances {

  static EnumInstance of(EnumStatement type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
