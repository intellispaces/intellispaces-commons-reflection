package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationElement {

  TestAnnotation[] arrayOfAnnotationElement();
}