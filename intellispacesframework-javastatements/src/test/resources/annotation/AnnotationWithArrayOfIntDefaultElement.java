package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfIntDefaultElement {

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };
}
