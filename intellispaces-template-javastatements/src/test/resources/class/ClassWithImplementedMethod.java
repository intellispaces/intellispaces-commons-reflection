package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface ClassWithImplementedMethod {

  @TesteeType
  class TesteeClass implements Interface1 {
    @Override
    public void method() {}
  }

  interface Interface1 {
    void method();
  }
}
