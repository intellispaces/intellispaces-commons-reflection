package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithEnumGetter() {

  public TestEnum enumGetter() {
    return null;
  }
}