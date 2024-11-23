package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithShortGetter() {

  public short shortGetter() {
    return 0;
  }
}