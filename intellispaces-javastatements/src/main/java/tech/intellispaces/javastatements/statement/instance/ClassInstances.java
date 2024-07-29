package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.statement.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
