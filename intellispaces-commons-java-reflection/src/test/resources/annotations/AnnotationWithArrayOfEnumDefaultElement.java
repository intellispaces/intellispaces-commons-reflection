package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}