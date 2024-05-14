package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithDoubleDefaultElement {

  double doubleElementDefault() default 1.0;
}