package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}