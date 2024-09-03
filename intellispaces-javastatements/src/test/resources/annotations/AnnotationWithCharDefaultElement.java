package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithCharDefaultElement {

  char charElementDefault() default 'a';
}