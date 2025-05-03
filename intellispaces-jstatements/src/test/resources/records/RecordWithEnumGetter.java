package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithEnumGetter() {

  public TestEnum enumGetter() {
    return null;
  }
}