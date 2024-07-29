package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithDoubleGetter() {

  public double doubleGetter() {
    return 0.0;
  }
}