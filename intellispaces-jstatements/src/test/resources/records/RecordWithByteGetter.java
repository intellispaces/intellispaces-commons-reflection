package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithByteGetter() {

  public byte byteGetter() {
    return 0;
  }
}