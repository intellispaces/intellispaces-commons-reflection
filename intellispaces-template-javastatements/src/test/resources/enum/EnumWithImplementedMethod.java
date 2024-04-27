package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

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
