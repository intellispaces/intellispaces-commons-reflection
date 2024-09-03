package intellispaces.common.javastatements.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
