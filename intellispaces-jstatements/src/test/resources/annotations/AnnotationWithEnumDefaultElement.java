package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithEnumDefaultElement {

  TestEnum enumElementDefault() default TestEnum.Value2;
}