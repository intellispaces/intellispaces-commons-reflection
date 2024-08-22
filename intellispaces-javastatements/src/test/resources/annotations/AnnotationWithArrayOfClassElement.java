package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassElement {

  Class[] arrayOfClassElement();
}