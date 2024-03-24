package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassElement {

  Class[] arrayOfClassElement();
}