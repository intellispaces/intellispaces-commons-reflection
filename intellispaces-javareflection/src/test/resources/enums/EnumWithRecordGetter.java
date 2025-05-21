package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public enum EnumWithRecordGetter {
  ;

  public TestRecord recordGetter() {
    return null;
  }
}