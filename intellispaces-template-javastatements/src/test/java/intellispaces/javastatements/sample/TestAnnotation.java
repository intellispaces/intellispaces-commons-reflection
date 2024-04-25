package intellispaces.javastatements.sample;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
