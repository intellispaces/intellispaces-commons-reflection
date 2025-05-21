package tech.intellispaces.javareflection.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
