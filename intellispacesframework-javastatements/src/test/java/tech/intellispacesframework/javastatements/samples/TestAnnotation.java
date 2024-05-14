package tech.intellispacesframework.javastatements.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
