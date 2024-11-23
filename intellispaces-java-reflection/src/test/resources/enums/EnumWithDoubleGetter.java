package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithDoubleGetter {
  ;

  public double doubleGetter() {
    return 0.0;
  }
}