package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public enum EnumWithDoubleGetter {
  ;

  public double doubleGetter() {
    return 0.0;
  }
}