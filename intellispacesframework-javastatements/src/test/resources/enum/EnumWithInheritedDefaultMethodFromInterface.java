package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

public interface EnumWithInheritedDefaultMethodFromInterface {

  @TesteeType
  enum TesteeEnum implements Interface1 {
    ;
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
