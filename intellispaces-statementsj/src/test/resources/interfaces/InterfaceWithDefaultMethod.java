package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithDefaultMethod {

  default void defaultMethod() {
  }
}