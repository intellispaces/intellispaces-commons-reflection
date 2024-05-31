package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

public class CyclicAnnotations {

  @TesteeType
  @SomeAnnotation
  interface SomeInterface {
  }

  /**
   * Cyclic annotation.
   */
  @SomeAnnotation
  @interface SomeAnnotation {
  }
}
