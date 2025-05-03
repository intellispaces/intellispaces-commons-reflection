package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithBooleanGetter() {

  public boolean booleanGetter() {
    return true;
  }
}