package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithRecordGetter {
  ;

  public TestRecord recordGetter() {
    return null;
  }
}