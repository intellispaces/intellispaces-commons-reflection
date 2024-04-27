package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.statement.custom.EnumStatement;

public interface EnumInstanceBuilder {

  static EnumInstance build(EnumStatement type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
