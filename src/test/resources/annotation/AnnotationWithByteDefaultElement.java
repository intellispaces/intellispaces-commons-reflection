package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithByteDefaultElement {

  byte byteElementDefault() default 1;
}