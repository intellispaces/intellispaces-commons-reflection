package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestEnum;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithEnumElement {

  TestEnum enumElement();
}