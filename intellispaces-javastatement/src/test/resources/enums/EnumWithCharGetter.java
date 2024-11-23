package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}