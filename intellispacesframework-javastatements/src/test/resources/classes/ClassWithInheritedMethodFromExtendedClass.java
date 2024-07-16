package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

public interface ClassWithInheritedMethodFromExtendedClass {

  @TesteeType
  class TesteeClass extends SuperClass {
    public void method1() {}
  }

  class SuperClass {
    public void method2() {}
  }
}
