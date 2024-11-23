package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

import java.io.IOException;

@TesteeType
public interface InterfaceWithMethodThrowsTwoExceptions {

  void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException;
}