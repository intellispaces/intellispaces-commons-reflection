package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfStringDefaultElement {

  String[] arrayOfStringElementDefault() default { "a", "b", "c" };
}