package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithShortGetter {
  ;

  public short shortGetter() {
    return 0;
  }
}