package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}