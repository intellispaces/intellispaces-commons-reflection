package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithByteGetter() {

  public byte byteGetter() {
    return 0;
  }
}