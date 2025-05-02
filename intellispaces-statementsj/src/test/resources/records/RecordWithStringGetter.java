package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithStringGetter() {

  public String stringGetter() {
    return null;
  }
}