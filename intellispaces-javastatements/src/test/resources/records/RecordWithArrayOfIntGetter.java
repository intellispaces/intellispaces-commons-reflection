package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithArrayOfIntGetter() {

  public int[] arrayOfIntGetter() {
    return null;
  }
}