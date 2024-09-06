package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

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
