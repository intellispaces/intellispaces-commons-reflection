package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithFloatDefaultElement {

  float floatElementDefault() default 1.0f;
}