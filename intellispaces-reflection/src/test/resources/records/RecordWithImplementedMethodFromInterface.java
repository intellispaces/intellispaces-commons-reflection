package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

public interface RecordWithImplementedMethodFromInterface {

  @TesteeType
  record TesteeRecord() implements Interface1 {
    public void method1() {}

    @Override
    public void method2() {}
  }

  interface Interface1 {
    void method2();
  }
}
