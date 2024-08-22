package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

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
