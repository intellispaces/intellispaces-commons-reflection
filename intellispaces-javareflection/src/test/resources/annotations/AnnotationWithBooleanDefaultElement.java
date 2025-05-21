package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithBooleanDefaultElement {

  boolean booleanElementDefault() default true;
}