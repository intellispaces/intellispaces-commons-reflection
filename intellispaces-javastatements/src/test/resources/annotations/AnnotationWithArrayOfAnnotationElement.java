package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.samples.TestAnnotation;
import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationElement {

  TestAnnotation[] arrayOfAnnotationElement();
}