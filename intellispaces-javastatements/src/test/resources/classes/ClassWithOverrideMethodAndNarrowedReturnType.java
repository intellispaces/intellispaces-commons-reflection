package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

public interface ClassWithOverrideMethodAndNarrowedReturnType {

  @TesteeType
  class TesteeClass extends SuperClass {
    @Override
    public String method() {
      return null;
    }
  }

  class SuperClass {
    public CharSequence method() {
      return null;
    }
  }
}
