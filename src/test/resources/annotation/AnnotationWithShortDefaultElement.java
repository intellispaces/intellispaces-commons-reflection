package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithShortDefaultElement {

  short shortElementDefault() default 1;
}