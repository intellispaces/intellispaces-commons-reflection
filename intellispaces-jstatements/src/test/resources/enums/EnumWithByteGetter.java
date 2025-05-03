package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithByteGetter {
  ;

  public byte byteGetter() {
    return 0;
  }
}