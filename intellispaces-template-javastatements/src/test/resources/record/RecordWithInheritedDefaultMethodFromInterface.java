package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface RecordWithInheritedDefaultMethodFromInterface {

  @TesteeType
  record TesteeRecord() implements Interface1 {
    public void method1() {}
  }

  interface Interface1 {
    default void method2() {}
  }
}
