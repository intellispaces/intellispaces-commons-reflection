package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntDefaultElement {

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };
}
