package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

public interface NestedInterface {

  public static interface NestedInterface1 {

    @TesteeType
    public static interface NestedInterface2 {

    }
  }
}