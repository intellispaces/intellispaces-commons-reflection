package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassDefaultElement {

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };
}