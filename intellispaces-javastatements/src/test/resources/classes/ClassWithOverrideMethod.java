package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

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
