package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public enum EnumWithByteGetter {
  ;

  public byte byteGetter() {
    return 0;
  }
}