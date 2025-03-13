package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

public interface ClassImplementedInterfaceWithGenericDefaultMethod1 {

  @TesteeType
  class TesteeClass implements Interface2 {
  }

  interface Interface2 extends Interface1<String, Integer> {
    @Override
    default Integer method(String param) {
      return null;
    }
  }

  interface Interface1<T, R> {
    R method(T param);
  }
}
