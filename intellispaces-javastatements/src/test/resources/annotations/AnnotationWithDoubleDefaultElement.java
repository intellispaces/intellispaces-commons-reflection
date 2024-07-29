package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}