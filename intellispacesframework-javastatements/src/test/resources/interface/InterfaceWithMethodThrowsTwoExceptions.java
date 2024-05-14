package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.io.IOException;

@TesteeType
public interface InterfaceWithMethodThrowsTwoExceptions {

  void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException;
}