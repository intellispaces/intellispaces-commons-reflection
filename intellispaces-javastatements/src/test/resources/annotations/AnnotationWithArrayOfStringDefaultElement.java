package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfStringDefaultElement {

  String[] arrayOfStringElementDefault() default { "a", "b", "c" };
}