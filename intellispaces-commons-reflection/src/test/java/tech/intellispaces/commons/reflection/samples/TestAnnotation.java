package tech.intellispaces.commons.reflection.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
