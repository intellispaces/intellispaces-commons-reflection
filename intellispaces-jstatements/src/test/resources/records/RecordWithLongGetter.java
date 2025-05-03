package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithLongGetter() {

  public long longGetter() {
    return 0;
  }
}