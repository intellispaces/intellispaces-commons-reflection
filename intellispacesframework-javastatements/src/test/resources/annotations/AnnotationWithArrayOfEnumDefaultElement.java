package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}