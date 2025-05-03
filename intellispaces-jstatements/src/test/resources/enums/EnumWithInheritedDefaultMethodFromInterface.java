package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

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
