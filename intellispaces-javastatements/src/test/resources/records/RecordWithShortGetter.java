package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithShortGetter() {

  public short shortGetter() {
    return 0;
  }
}