package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithFloatGetter() {

  public float floatGetter() {
    return 0.0f;
  }
}