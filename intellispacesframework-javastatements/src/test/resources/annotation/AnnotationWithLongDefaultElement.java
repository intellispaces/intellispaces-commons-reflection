package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithLongDefaultElement {

  long longElementDefault() default 1L;
}