package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithFloatDefaultElement {

  float floatElementDefault() default 1.0f;
}