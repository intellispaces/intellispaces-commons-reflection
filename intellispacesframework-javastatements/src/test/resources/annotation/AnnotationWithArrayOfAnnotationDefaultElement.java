package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.samples.TestAnnotation;
import tech.intellispaces.framework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationDefaultElement {

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };
}