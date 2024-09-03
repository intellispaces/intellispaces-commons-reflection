package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

import java.io.IOException;

@TesteeType
public interface InterfaceWithMethodThrowsTwoExceptions {

  void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException;
}