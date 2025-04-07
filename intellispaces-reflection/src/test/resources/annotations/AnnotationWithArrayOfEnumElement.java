package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumElement {

  TestEnum[] arrayOfEnumElement();
}