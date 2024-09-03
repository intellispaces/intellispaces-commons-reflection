package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithStringDefaultElement {

  String stringElementDefault() default "abc";
}
