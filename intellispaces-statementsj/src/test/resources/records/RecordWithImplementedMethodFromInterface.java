package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

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
