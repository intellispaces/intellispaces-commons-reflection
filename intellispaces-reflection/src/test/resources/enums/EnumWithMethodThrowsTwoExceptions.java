package tech.intellispaces.reflection.samples;

import java.io.IOException;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithMethodThrowsTwoExceptions {
  ;

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}