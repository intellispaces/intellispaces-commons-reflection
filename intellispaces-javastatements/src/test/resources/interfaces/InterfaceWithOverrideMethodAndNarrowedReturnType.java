package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

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
