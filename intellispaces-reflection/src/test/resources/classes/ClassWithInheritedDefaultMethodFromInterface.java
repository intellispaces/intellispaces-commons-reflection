package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

public interface ClassWithInheritedDefaultMethodFromInterface {

  @TesteeType
  class TesteeClass implements Interface1 {
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
