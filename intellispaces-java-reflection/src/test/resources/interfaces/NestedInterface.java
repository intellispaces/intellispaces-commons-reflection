package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

public interface NestedInterface {

  public static interface NestedInterface1 {

    @TesteeType
    public static interface NestedInterface2 {

    }
  }
}