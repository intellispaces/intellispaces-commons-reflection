package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfStringDefaultElement {

  String[] arrayOfStringElementDefault() default { "a", "b", "c" };
}