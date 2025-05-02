package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationElement {

  TestAnnotation[] arrayOfAnnotationElement();
}