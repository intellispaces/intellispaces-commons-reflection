package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface RecordWithInheritedDefaultMethodFromInterface {

  @TesteeType
  record TesteeRecord() implements Interface1 {
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
