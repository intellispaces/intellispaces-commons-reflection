package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithBooleanGetter() {

  public boolean booleanGetter() {
    return true;
  }
}