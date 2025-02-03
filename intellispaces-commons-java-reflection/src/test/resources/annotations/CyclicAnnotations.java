package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

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
