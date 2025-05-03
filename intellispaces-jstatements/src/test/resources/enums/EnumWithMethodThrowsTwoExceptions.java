package tech.intellispaces.jstatements.samples;

import java.io.IOException;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithMethodThrowsTwoExceptions {
  ;

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}