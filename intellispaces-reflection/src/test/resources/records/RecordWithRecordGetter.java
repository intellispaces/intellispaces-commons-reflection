package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}