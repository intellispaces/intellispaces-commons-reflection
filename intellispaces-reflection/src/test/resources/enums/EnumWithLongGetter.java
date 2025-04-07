package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithLongGetter {
  ;

  public long longGetter() {
    return 0;
  }
}