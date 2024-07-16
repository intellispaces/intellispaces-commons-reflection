package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

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
