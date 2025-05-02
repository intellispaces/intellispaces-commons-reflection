package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}