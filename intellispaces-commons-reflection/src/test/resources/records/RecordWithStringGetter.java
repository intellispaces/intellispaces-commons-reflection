package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public record RecordWithStringGetter() {

  public String stringGetter() {
    return null;
  }
}