package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithByteDefaultElement {

  byte byteElementDefault() default 1;
}