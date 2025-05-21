package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public class ClassWithMethodThrowsTwoExceptions {

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}