package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

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
