package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithFloatGetter() {

  public float floatGetter() {
    return 0.0f;
  }
}