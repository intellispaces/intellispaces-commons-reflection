package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

public interface EnumImplementedTwoInterfaces {

  @TesteeType
  enum TesteeEnum implements Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
