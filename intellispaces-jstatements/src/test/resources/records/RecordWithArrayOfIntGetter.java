package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithArrayOfIntGetter() {

  public int[] arrayOfIntGetter() {
    return null;
  }
}