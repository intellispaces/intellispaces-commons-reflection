package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public class ClassWithMethodThrowsTwoExceptions {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}