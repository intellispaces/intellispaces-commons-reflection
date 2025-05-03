package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}