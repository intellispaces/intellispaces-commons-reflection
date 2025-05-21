package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public record RecordWithDoubleArrayOfStringGetter() {

  public String[][] doubleArrayOfStringGetter() {
    return null;
  }
}