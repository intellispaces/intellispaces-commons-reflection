package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfStringElement {

  String[] arrayOfStringElement();
}