package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.statement.custom.CustomType;

public interface ClassInstanceBuilder {

  static ClassInstance build(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
