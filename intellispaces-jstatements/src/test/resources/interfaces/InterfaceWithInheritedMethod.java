package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

public interface InterfaceWithInheritedMethod {

  @TesteeType
  interface TesteeInterface extends ParentInterface {
    void method1();
  }

  interface ParentInterface {
    void method2();
  }
}
