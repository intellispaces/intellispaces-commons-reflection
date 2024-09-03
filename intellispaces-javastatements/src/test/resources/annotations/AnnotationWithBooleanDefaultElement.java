package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithBooleanDefaultElement {

  boolean booleanElementDefault() default true;
}