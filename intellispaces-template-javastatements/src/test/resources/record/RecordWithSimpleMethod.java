package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithSimpleMethod() {

  public void simpleMethod() {
    return;
  }
}