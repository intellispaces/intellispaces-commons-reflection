package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}