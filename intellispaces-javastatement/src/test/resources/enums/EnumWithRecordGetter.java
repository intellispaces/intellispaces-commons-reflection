package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.samples.TestRecord;
import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public enum EnumWithRecordGetter {
  ;

  public TestRecord recordGetter() {
    return null;
  }
}