package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithShortGetter() {

  public short shortGetter() {
    return 0;
  }
}