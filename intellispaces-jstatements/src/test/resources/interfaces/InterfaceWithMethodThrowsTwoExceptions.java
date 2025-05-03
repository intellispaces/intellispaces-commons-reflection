package tech.intellispaces.jstatements.samples;

import java.io.IOException;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodThrowsTwoExceptions {

  void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException;
}