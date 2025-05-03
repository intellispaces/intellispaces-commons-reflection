package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithShortGetter {
  ;

  public short shortGetter() {
    return 0;
  }
}