package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}