package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface ClassWithInheritedDefaultMethodFromInterface {

  @TesteeType
  class TesteeClass implements Interface1 {
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
