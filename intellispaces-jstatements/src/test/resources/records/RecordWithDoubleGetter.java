package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithDoubleGetter() {

  public double doubleGetter() {
    return 0.0;
  }
}