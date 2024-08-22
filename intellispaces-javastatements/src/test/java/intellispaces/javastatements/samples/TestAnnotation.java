package intellispaces.javastatements.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
