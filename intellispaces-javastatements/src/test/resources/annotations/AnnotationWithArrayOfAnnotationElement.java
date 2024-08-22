package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestAnnotation;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationElement {

  TestAnnotation[] arrayOfAnnotationElement();
}