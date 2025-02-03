package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithBooleanGetter() {

  public boolean booleanGetter() {
    return true;
  }
}