package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithByteGetter {
  ;

  public byte byteGetter() {
    return 0;
  }
}