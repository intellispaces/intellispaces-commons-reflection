package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.sample.TestEnum;
import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumElement {

  TestEnum[] arrayOfEnumElement();
}