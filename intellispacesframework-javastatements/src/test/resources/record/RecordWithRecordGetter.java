package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}