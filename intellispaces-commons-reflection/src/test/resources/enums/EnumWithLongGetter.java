package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public enum EnumWithLongGetter {
  ;

  public long longGetter() {
    return 0;
  }
}