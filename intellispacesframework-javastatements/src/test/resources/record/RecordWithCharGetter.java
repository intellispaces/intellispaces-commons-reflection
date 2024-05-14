package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithCharGetter() {

  public char charGetter() {
    return 'a';
  }
}