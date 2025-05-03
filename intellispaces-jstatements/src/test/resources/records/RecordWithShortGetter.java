package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithShortGetter() {

  public short shortGetter() {
    return 0;
  }
}