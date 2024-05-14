package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithEnumDefaultElement {

  TestEnum enumElementDefault() default TestEnum.Value2;
}