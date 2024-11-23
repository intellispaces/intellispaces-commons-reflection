package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface InterfaceWithInheritedMethod {

  @TesteeType
  interface TesteeInterface extends ParentInterface {
    void method1();
  }

  interface ParentInterface {
    void method2();
  }
}
