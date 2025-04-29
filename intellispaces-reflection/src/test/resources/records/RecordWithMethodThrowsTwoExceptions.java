package tech.intellispaces.reflection.samples;

import java.io.IOException;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithMethodThrowsTwoExceptions() {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}