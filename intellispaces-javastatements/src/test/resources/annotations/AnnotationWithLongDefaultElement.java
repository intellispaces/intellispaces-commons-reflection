package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithLongDefaultElement {

  long longElementDefault() default 1L;
}