package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithShortDefaultElement {

  short shortElementDefault() default 1;
}