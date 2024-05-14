package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface RecordWithOverrideMethodAndNarrowedReturnType {

  @TesteeType
  record TesteeRecord() implements Interface1 {
    @Override
    public String method() {
      return null;
    }
  }

  interface Interface1 {
    CharSequence method();
  }
}
