package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithDoubleGetter() {

  public double doubleGetter() {
    return 0.0;
  }
}