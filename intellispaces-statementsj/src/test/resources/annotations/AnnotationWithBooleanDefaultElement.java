package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithBooleanDefaultElement {

  boolean booleanElementDefault() default true;
}