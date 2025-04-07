package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithFloatGetter {
  ;

  public float floatGetter() {
    return 0.0f;
  }
}