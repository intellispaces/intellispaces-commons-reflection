package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithDoubleGetter() {

  public double doubleGetter() {
    return 0.0;
  }
}