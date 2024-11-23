package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithDoubleGetter() {

  public double doubleGetter() {
    return 0.0;
  }
}