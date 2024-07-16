package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

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
