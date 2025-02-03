package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}