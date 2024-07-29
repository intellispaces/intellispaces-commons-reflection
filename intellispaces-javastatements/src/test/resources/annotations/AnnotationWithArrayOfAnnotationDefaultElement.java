package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.samples.TestAnnotation;
import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationDefaultElement {

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };
}