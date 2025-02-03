package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithIntGetter {
  ;

  public int intGetter() {
    return 0;
  }
}