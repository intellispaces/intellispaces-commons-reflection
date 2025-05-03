package tech.intellispaces.jstatements.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
