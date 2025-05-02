package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithCharGetter() {

  public char charGetter() {
    return 'a';
  }
}