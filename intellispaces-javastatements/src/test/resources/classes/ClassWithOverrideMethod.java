package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

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
