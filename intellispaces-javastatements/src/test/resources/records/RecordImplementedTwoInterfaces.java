package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

public interface RecordImplementedTwoInterfaces {

  @TesteeType
  record TesteeRecord() implements Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
