package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

public interface ClassInstances {

  static ClassInstance of(CustomStatement type) {
    return new ClassInstanceImpl(type);
  }
}
