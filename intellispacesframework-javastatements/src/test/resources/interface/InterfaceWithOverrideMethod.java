package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface InterfaceWithOverrideMethod {

  @TesteeType
  interface TesteeInterface extends SuperInterface {
    @Override
    void method();
  }

  interface SuperInterface {
    void method();
  }
}
