package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

public interface InterfaceWithOverrideMethodAndNarrowedReturnType {

  @TesteeType
  interface TesteeInterface extends Interface1 {
    @Override
    String method();
  }

  interface Interface1 {
    CharSequence method();
  }
}
