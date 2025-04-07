package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithIntGetter {
  ;

  public int intGetter() {
    return 0;
  }
}