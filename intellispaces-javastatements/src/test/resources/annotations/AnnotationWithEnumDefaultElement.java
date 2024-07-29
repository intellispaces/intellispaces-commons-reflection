package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.samples.TestEnum;
import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithEnumDefaultElement {

  TestEnum enumElementDefault() default TestEnum.Value2;
}