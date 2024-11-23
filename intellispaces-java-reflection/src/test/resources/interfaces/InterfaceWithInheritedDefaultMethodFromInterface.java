package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface InterfaceWithInheritedDefaultMethodFromInterface {

  @TesteeType
  interface TesteeInterface extends Interface1 {
    void method1();
  }

  interface Interface1 {
    default void method2() {}
  }
}
