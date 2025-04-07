package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithDoubleGetter {
  ;

  public double doubleGetter() {
    return 0.0;
  }
}