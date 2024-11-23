package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.samples.TestRecord;
import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}