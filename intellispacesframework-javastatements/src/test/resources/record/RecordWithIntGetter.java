package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithIntGetter() {

  public int intGetter() {
    return 0;
  }
}