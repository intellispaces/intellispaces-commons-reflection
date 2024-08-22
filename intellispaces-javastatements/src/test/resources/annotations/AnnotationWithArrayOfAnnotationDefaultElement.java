package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestAnnotation;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationDefaultElement {

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };
}