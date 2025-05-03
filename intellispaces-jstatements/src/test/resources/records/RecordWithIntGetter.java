package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithIntGetter() {

  public int intGetter() {
    return 0;
  }
}