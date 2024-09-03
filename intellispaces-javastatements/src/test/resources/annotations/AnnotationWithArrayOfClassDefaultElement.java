package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassDefaultElement {

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };
}