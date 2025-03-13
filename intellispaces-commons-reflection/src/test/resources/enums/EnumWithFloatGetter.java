package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public enum EnumWithFloatGetter {
  ;

  public float floatGetter() {
    return 0.0f;
  }
}