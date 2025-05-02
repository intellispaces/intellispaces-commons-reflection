package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithByteGetter {
  ;

  public byte byteGetter() {
    return 0;
  }
}