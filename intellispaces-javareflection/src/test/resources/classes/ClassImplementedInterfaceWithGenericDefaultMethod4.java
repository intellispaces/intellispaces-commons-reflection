package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

public interface ClassImplementedInterfaceWithGenericDefaultMethod4 {

  @TesteeType
  class TesteeClass implements Interface2 {
  }

  interface Interface2 extends Interface1 {
    @Override
    default <T, R> R method(T param1, Object... param2) {
      return null;
    }
  }

  interface Interface1 {
    <T, R> R method(T param1, Object... param2);
  }
}
