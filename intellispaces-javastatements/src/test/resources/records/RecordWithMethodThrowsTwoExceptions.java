package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

import java.io.IOException;

@TesteeType
public record RecordWithMethodThrowsTwoExceptions() {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}