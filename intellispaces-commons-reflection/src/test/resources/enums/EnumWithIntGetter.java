package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public enum EnumWithIntGetter {
  ;

  public int intGetter() {
    return 0;
  }
}