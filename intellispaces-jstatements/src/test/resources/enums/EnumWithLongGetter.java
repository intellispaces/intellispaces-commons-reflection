package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithLongGetter {
  ;

  public long longGetter() {
    return 0;
  }
}