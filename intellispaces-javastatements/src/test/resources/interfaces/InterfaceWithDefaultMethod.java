package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public interface InterfaceWithDefaultMethod {

  default void defaultMethod() {
  }
}