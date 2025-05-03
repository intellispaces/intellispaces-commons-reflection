package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

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
