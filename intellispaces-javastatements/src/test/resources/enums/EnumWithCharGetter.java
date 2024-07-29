package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}