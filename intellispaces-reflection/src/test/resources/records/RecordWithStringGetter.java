package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithStringGetter() {

  public String stringGetter() {
    return null;
  }
}