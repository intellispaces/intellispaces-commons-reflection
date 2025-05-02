package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithStringDefaultElement {

  String stringElementDefault() default "abc";
}
