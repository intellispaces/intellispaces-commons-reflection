package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithByteGetter() {

  public byte byteGetter() {
    return 0;
  }
}