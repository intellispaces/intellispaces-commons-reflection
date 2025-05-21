package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

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
