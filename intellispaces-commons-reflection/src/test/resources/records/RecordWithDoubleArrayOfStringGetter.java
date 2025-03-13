package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public record RecordWithDoubleArrayOfStringGetter() {

  public String[][] doubleArrayOfStringGetter() {
    return null;
  }
}