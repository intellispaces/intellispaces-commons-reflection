package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithIntDefaultElement {

  int intElementDefault() default 1;
}