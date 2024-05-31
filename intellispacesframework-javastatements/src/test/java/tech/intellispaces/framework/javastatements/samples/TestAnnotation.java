package tech.intellispaces.framework.javastatements.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
