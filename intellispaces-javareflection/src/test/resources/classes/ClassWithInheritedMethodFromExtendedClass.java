package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

public interface ClassWithInheritedMethodFromExtendedClass {

  @TesteeType
  class TesteeClass extends SuperClass {
    public void method1() {}
  }

  class SuperClass {
    public void method2() {}
  }
}
