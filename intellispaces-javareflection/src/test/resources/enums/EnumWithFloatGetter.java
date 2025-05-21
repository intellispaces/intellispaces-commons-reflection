package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public enum EnumWithFloatGetter {
  ;

  public float floatGetter() {
    return 0.0f;
  }
}