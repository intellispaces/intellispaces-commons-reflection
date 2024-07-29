package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithSimpleMethod() {

  public void simpleMethod() {
    return;
  }
}