package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

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
