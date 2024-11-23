package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithIntDefaultElement {

  int intElementDefault() default 1;
}