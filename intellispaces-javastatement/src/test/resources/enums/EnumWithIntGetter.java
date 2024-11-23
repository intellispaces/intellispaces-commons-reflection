package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithIntGetter {
  ;

  public int intGetter() {
    return 0;
  }
}