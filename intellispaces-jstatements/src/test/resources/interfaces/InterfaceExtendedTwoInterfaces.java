package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

public interface InterfaceExtendedTwoInterfaces {

  @TesteeType
  interface TesteeInterface extends Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
