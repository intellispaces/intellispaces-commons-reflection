package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithFloatDefaultElement {

  float floatElementDefault() default 1.0f;
}