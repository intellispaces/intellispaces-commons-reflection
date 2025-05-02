package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithArrayOfIntGetter() {

  public int[] arrayOfIntGetter() {
    return null;
  }
}