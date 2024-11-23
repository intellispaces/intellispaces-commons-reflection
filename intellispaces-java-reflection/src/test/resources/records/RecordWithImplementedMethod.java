package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface RecordWithImplementedMethod {

  @TesteeType
  record TesteeRecord() implements Interface1 {
    @Override
    public void method() {}
  }

  interface Interface1 {
    void method();
  }
}
