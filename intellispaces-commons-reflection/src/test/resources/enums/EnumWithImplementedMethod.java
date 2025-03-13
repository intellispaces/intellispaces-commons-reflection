package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

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
