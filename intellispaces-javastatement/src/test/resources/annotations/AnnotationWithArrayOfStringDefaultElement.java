package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfStringDefaultElement {

  String[] arrayOfStringElementDefault() default { "a", "b", "c" };
}