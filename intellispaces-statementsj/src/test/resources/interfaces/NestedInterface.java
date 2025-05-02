package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

public interface NestedInterface {

  public static interface NestedInterface1 {

    @TesteeType
    public static interface NestedInterface2 {

    }
  }
}