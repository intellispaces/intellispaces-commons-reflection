package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.samples.TestEnum;
import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}