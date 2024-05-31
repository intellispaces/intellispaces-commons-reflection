package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

public interface ClassInstanceBuilder {

  static ClassInstance build(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
