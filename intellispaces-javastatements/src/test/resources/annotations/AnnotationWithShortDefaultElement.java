package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithShortDefaultElement {

  short shortElementDefault() default 1;
}