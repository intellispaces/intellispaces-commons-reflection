package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public interface InterfaceWithDefaultMethod {

  default void defaultMethod() {
  }
}