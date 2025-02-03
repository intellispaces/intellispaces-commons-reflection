package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface ClassImplementedInterfaceWithDefaultMethod1 {

  @TesteeType
  class TesteeClass implements Interface2 {
  }

  interface Interface2 extends Interface1 {
    default int method(String param) {
      return 0;
    }
  }

  interface Interface1 {
    int method(String param);
  }
}
