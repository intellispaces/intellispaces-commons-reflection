package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithByteGetter() {

  public byte byteGetter() {
    return 0;
  }
}