package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface InterfaceWithInheritedMethod {

  @TesteeType
  interface TesteeInterface extends ParentInterface {
    void method1();
  }

  interface ParentInterface {
    void method2();
  }
}
