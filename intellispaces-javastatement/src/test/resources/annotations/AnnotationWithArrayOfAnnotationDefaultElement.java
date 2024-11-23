package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.samples.TestAnnotation;
import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationDefaultElement {

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };
}