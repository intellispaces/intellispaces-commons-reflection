package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestEnum;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumDefaultElement {

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };
}