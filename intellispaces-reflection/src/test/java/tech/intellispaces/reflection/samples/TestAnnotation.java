package tech.intellispaces.reflection.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
