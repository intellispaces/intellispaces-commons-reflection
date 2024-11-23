package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithByteGetter {
  ;

  public byte byteGetter() {
    return 0;
  }
}