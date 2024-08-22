package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithStringDefaultElement {

  String stringElementDefault() default "abc";
}
