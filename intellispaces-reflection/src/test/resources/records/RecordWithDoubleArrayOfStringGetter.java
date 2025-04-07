package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithDoubleArrayOfStringGetter() {

  public String[][] doubleArrayOfStringGetter() {
    return null;
  }
}