package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

public interface ClassExtendedSuperClassAndImplementedTwoInterfaces {

  @TesteeType
  class TesteeClass extends SuperClass implements Interface1, Interface2 {
  }

  class SuperClass {
  }

  interface Interface1 {
  }

  interface Interface2 {
  }
}
