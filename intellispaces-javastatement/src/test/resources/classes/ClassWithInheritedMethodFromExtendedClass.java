package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

public interface ClassWithInheritedMethodFromExtendedClass {

  @TesteeType
  class TesteeClass extends SuperClass {
    public void method1() {}
  }

  class SuperClass {
    public void method2() {}
  }
}
