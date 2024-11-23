package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithLongGetter {
  ;

  public long longGetter() {
    return 0;
  }
}