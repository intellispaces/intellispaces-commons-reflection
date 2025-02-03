package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntDefaultElement {

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };
}
