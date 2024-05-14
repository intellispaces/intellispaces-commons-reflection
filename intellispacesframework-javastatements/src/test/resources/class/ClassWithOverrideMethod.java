package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface ClassWithOverrideMethod {

  @TesteeType
  class TesteeClass extends SuperClass {
    @Override
    public void method() {}
  }

  class SuperClass {
    public void method() {}
  }
}
