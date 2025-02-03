package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

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
