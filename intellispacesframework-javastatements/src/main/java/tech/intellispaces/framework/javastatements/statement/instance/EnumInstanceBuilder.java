package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;

public interface EnumInstanceBuilder {

  static EnumInstance build(EnumStatement type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
