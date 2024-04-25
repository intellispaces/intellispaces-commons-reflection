package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

public interface EnumWithOverrideMethodAndNarrowedReturnType {

  @TesteeType
  enum TesteeEnum implements Interface1 {
    ;
    @Override
    public String method() {
      return null;
    }
  }

  interface Interface1 {
    CharSequence method();
  }
}
