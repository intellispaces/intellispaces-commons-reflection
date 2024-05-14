package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

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
