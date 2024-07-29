package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithLongGetter() {

  public long longGetter() {
    return 0;
  }
}