package tech.intellispaces.reflection.samples;

import java.io.IOException;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public class ClassWithMethodThrowsTwoExceptions {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}