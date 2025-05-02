package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithBooleanGetter {
  ;

  public boolean booleanGetter() {
    return true;
  }
}