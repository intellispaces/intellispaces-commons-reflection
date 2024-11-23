package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface EnumImplementedTwoInterfaces {

  @TesteeType
  enum TesteeEnum implements Interface1, Interface2 {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
