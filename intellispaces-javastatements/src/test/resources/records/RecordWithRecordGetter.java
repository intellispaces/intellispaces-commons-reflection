package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.samples.TestRecord;
import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}