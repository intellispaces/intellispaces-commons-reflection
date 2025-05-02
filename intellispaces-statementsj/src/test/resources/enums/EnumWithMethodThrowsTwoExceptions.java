package tech.intellispaces.statementsj.samples;

import java.io.IOException;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithMethodThrowsTwoExceptions {
  ;

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}