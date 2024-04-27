package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public interface InterfaceWithDefaultMethod {

  default void defaultMethod() {
  }
}