package intellispaces.common.javastatement.samples;

public @interface TestAnnotation {

  String value();

  String otherValue() default "defaultString";
}
