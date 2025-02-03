package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithDoubleGetter {
  ;

  public double doubleGetter() {
    return 0.0;
  }
}