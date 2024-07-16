package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

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
