package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

public interface ClassWithInheritedMethodFromExtendedClass {

  @TesteeType
  class TesteeClass extends SuperClass {
    public void method1() {}
  }

  class SuperClass {
    public void method2() {}
  }
}
