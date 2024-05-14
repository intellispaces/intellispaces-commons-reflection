package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface InterfaceExtendedTwoInterfaces {

  @TesteeType
  interface TesteeInterface extends Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
