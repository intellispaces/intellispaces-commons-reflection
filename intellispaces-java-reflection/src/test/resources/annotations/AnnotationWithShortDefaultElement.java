package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithShortDefaultElement {

  short shortElementDefault() default 1;
}