package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithBooleanGetter {
  ;

  public boolean booleanGetter() {
    return true;
  }
}