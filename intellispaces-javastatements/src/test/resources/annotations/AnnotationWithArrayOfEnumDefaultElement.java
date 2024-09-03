package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.samples.TestEnum;
import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}