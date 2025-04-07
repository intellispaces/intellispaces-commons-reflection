package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public class ClassWithMethodThrowsTwoExceptions {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}