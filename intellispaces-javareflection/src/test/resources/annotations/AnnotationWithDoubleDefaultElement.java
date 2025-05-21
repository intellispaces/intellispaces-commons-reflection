package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}