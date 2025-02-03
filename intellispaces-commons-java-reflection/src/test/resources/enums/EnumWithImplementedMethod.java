package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public interface EnumWithImplementedMethod {

  @TesteeType
  enum TesteeEnum implements Interface1 {
    ;
    @Override
    public void method() {}
  }

  interface Interface1 {
    void method();
  }
}
