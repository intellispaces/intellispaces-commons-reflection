package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

public interface ClassImplementedInterfaceWithGenericDefaultMethod2 {

  @TesteeType
  class TesteeClass implements Interface2<String, Integer> {
  }

  interface Interface2<T, R> extends Interface1<T, R> {
    @Override
    default R method(T param1, Object... param2) {
      return null;
    }
  }

  interface Interface1<T, R> {
    R method(T param1, Object... param2);
  }
}
