package tech.intellispaces.statementsj.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
