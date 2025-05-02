package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithFloatGetter() {

  public float floatGetter() {
    return 0.0f;
  }
}