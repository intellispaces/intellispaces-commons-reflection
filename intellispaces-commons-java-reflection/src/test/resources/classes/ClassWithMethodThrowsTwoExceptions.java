package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public class ClassWithMethodThrowsTwoExceptions {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}