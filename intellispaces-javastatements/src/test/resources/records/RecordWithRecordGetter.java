package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestRecord;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}