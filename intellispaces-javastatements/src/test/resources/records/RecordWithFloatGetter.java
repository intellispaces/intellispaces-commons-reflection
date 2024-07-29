package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithFloatGetter() {

  public float floatGetter() {
    return 0.0f;
  }
}