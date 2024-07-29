package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithBooleanGetter {
  ;

  public boolean booleanGetter() {
    return true;
  }
}