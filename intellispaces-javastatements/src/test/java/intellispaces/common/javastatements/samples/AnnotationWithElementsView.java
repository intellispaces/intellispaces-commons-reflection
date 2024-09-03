package intellispaces.common.javastatements.samples;

public @interface AnnotationWithElementsView {

  byte byteElement();

  short shortElement();

  int intElement();

  long longElement();

  float floatElement();

  double doubleElement();

  char charElement();

  boolean booleanElement();

  String stringElement();

  int[] arrayOfIntElement();

  String[] arrayOfStringElement();

  TestEnum[] arrayOfEnumElement();

  Class[] arrayOfClassElement();

  TestAnnotation[] arrayOfAnnotationElement();

  TestEnum enumElement();

  byte byteElementDefault() default 1;

  short shortElementDefault() default 1;

  int intElementDefault() default 1;

  long longElementDefault() default 1L;

  float floatElementDefault() default 1.0f;

  double doubleElementDefault() default 1.0;

  char charElementDefault() default 'a';

  boolean booleanElementDefault() default true;

  String stringElementDefault() default "abc";

  int[] arrayOfIntElementDefault() default { 1, 2, 3 };

  String[] arrayOfStringElementDefault() default { "a", "b", "c" };

  TestEnum[] arrayOfEnumElementDefault() default { TestEnum.Value1, TestEnum.Value3 };

  Class[] arrayOfClassElementDefault() default { Integer.class, String.class };

  TestAnnotation[] arrayOfAnnotationElementDefault() default { @TestAnnotation("a") };

  TestEnum enumElementDefault() default TestEnum.Value2;
}
