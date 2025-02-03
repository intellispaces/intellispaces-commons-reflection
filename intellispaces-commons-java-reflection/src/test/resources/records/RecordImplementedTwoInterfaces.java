package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface RecordImplementedTwoInterfaces {

  @TesteeType
  record TesteeRecord() implements Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
