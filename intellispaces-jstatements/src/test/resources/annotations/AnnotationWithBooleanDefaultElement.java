package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithBooleanDefaultElement {

  boolean booleanElementDefault() default true;
}