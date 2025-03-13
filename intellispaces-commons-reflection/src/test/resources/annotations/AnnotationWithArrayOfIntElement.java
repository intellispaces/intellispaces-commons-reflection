package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntElement {

  int[] arrayOfIntElement();
}
