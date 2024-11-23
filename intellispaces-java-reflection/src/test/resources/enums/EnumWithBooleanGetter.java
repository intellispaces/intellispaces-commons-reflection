package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithBooleanGetter {
  ;

  public boolean booleanGetter() {
    return true;
  }
}