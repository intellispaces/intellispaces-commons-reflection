package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithIntGetter() {

  public int intGetter() {
    return 0;
  }
}