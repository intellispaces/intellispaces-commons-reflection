package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.samples.TestEnum;
import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfEnumElement {

  TestEnum[] arrayOfEnumElement();
}