package tech.intellispaces.framework.javastatements.samples;

import tech.intellispaces.framework.javastatements.samples.TestEnum;
import tech.intellispaces.framework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithEnumDefaultElement {

  TestEnum enumElementDefault() default TestEnum.Value2;
}