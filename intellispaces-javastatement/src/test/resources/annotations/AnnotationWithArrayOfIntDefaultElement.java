package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntDefaultElement {

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };
}
