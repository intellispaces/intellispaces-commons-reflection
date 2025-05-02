package tech.intellispaces.statementsj.samples;

import java.io.IOException;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodThrowsTwoExceptions {

  void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException;
}