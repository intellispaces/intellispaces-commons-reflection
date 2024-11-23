package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}