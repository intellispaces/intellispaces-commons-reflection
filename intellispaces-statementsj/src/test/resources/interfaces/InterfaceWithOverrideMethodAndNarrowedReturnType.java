package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

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
