package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface EnumImplementedTwoInterfaces {

  @TesteeType
  enum TesteeEnum implements Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
