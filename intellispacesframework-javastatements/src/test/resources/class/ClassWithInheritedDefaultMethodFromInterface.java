package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface ClassWithInheritedDefaultMethodFromInterface {

  @TesteeType
  class TesteeClass implements Interface1 {
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
