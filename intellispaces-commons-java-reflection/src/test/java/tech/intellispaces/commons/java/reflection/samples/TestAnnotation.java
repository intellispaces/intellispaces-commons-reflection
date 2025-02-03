package tech.intellispaces.commons.java.reflection.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
