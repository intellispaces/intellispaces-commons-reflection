package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

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
