package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithBooleanGetter() {

  public boolean booleanGetter() {
    return true;
  }
}