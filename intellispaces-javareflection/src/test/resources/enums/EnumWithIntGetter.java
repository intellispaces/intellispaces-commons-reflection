package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public enum EnumWithIntGetter {
  ;

  public int intGetter() {
    return 0;
  }
}