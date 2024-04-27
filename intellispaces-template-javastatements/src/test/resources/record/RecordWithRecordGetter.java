package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.sample.TestRecord;
import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}