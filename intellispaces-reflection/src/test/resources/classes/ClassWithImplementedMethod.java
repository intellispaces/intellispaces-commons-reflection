package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

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
