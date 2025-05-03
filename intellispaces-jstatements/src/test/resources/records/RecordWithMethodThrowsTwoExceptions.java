package tech.intellispaces.jstatements.samples;

import java.io.IOException;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithMethodThrowsTwoExceptions() {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}