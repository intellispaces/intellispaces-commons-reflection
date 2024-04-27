package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.sample.TestEnum;
import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithEnumGetter() {

  public TestEnum enumGetter() {
    return null;
  }
}