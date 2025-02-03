package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface InterfaceWithInheritedMethod {

  @TesteeType
  interface TesteeInterface extends ParentInterface {
    void method1();
  }

  interface ParentInterface {
    void method2();
  }
}
