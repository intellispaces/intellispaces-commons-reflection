package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

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
