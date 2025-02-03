package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface InterfaceWithOverrideMethod {

  @TesteeType
  interface TesteeInterface extends SuperInterface {
    @Override
    void method();
  }

  interface SuperInterface {
    void method();
  }
}
