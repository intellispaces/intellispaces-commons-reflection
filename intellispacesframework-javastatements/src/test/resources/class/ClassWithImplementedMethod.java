package tech.intellispacesframework.javastatements.samples;

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
