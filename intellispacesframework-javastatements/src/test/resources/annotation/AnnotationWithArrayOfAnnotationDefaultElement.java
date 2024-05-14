package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfAnnotationDefaultElement {

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };
}