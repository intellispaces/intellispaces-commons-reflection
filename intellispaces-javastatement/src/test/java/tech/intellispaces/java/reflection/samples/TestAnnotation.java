package tech.intellispaces.java.reflection.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
