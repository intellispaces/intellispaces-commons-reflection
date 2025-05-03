package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassDefaultElement {

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };
}