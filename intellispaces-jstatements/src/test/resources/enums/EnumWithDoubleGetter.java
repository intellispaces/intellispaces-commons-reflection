package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithDoubleGetter {
  ;

  public double doubleGetter() {
    return 0.0;
  }
}