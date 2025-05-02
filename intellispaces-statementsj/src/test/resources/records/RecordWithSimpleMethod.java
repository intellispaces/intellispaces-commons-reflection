package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithSimpleMethod() {

  public void simpleMethod() {
    return;
  }
}