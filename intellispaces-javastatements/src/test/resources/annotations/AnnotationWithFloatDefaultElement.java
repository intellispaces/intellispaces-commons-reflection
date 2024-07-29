package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithFloatDefaultElement {

  float floatElementDefault() default 1.0f;
}