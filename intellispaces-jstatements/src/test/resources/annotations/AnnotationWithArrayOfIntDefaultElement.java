package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntDefaultElement {

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };
}
