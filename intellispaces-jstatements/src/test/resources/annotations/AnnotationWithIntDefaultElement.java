package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithIntDefaultElement {

  int intElementDefault() default 1;
}