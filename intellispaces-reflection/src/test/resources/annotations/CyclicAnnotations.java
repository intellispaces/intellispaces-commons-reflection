package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

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
