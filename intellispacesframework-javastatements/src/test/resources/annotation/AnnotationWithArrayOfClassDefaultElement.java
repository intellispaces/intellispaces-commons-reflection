package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public @interface AnnotationWithArrayOfClassDefaultElement {

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };
}