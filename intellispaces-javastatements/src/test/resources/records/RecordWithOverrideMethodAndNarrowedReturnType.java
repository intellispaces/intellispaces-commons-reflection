package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

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
