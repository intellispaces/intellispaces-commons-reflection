package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithCharGetter() {

  public char charGetter() {
    return 'a';
  }
}