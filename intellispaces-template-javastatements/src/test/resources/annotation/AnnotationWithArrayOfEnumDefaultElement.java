package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.sample.TestEnum;
import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}