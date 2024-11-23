package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithStringDefaultElement {

  String stringElementDefault() default "abc";
}
