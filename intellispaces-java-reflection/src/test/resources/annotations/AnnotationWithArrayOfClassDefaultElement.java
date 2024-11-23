package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassDefaultElement {

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };
}