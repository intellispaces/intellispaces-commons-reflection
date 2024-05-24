package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

public interface NestedInterface {

  public static interface NestedInterface1 {

    @TesteeType
    public static interface NestedInterface2 {

    }
  }
}