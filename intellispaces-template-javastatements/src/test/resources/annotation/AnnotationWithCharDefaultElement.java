package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithCharDefaultElement {

  char charElementDefault() default 'a';
}