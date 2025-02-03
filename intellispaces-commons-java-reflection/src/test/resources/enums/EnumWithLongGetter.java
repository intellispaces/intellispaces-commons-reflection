package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithLongGetter {
  ;

  public long longGetter() {
    return 0;
  }
}