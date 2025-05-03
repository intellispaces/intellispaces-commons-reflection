package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

public interface ClassWithImplementedMethodFromInterface {

  @TesteeType
  class TesteeClass implements Interface1 {
    public void method1() {}

    @Override
    public void method2() {}
  }

  interface Interface1 {
    void method2();
  }
}
