package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithRecordGetter() {

  public TestRecord recordGetter() {
    return null;
  }
}