package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public record RecordWithStringGetter() {

  public String stringGetter() {
    return null;
  }
}