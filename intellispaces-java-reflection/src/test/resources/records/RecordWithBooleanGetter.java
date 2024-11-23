package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithBooleanGetter() {

  public boolean booleanGetter() {
    return true;
  }
}