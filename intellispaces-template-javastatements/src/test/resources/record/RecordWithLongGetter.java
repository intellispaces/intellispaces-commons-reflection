package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithLongGetter() {

  public long longGetter() {
    return 0;
  }
}