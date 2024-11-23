package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithFloatGetter() {

  public float floatGetter() {
    return 0.0f;
  }
}