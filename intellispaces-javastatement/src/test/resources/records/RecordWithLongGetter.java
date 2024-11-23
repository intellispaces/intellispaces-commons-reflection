package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record RecordWithLongGetter() {

  public long longGetter() {
    return 0;
  }
}