package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public record RecordWithMethodThrowsTwoExceptions() {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}