package tech.intellispaces.framework.javastatements;

import org.junit.jupiter.api.Test;
import tech.intellispaces.framework.javastatements.samples.AnnotationWithElementsView;
import tech.intellispaces.framework.javastatements.samples.TestAnnotation;
import tech.intellispaces.framework.javastatements.samples.TestEnum;
import tech.intellispaces.framework.javastatements.support.TesteeType;
import tech.intellispaces.framework.commons.action.Handler;
import tech.intellispaces.framework.commons.datahandle.HandleFunctions;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.session.SessionBuilder;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatement;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatement;
import tech.intellispaces.framework.javastatements.statement.custom.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.reference.PrimitiveTypeReferences;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AnnotationStatement}.
 */
public class AnnotationTest extends AbstractCustomTypeTest {

  @Test
  public void testEmptyAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotation/EmptyAnnotation.java");

    // When
    CustomType customTypeStatement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customTypeStatement).isInstanceOf(AnnotationStatement.class);
    AnnotationStatement annotationStatement = customTypeStatement.asAnnotation().orElse(null);
    assertThat(annotationStatement).isNotNull();

    assertThat(annotationStatement.isAbstract()).isTrue();
    assertThat(annotationStatement.simpleName()).isEqualTo("EmptyAnnotation");
    assertThat(annotationStatement.canonicalName()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EmptyAnnotation");
    assertThat(annotationStatement.className()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EmptyAnnotation");

    assertThat(annotationStatement.typeParameters()).isEmpty();

    assertThat(annotationStatement.parentTypes()).hasSize(1);
    assertThat(annotationStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Annotation.class.getCanonicalName());

    assertThat(annotationStatement.declaredMethods()).isEmpty();

    assertThat(annotationStatement.annotations()).hasSize(1);
    HandleFunctions.handle(annotationStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.framework.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testAnnotationWithByteElement() {
    testAnnotationWithElement("AnnotationWithByteElement", "byteElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Byte);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithByteDefaultElement() {
    testAnnotationWithElement("AnnotationWithByteDefaultElement", "byteElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Byte);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Byte);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((byte) 1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortElement() {
    testAnnotationWithElement("AnnotationWithShortElement", "shortElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Short);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortDefaultElement() {
    testAnnotationWithElement("AnnotationWithShortDefaultElement", "shortElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Short);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Short);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((short) 1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntElement() {
    testAnnotationWithElement("AnnotationWithIntElement", "intElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Integer);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithIntDefaultElement", "intElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Integer);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Integer);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongElement() {
    testAnnotationWithElement("AnnotationWithLongElement", "longElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Long);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongDefaultElement() {
    testAnnotationWithElement("AnnotationWithLongDefaultElement", "longElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Long);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Long);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1L);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatElement() {
    testAnnotationWithElement("AnnotationWithFloatElement", "floatElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Float);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatDefaultElement() {
    testAnnotationWithElement("AnnotationWithFloatDefaultElement", "floatElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Float);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Float);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0f);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleElement() {
    testAnnotationWithElement("AnnotationWithDoubleElement", "doubleElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Double);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleDefaultElement() {
    testAnnotationWithElement("AnnotationWithDoubleDefaultElement", "doubleElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Double);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Double);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharElement() {
    testAnnotationWithElement("AnnotationWithCharElement", "charElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Char);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharDefaultElement() {
    testAnnotationWithElement("AnnotationWithCharDefaultElement", "charElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Char);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Char);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo('a');
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanElement() {
    testAnnotationWithElement("AnnotationWithBooleanElement", "booleanElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Boolean);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanDefaultElement() {
    testAnnotationWithElement("AnnotationWithBooleanDefaultElement", "booleanElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Boolean);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveTypeReferences.Boolean);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(true);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringElement() {
    testAnnotationWithElement("AnnotationWithStringElement", "stringElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithStringDefaultElement", "stringElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().typeArguments()).isEmpty();
      assertThat(elementMethod.defaultValue().orElseThrow().asString().orElseThrow().value()).isEqualTo("abc");
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntElement", "arrayOfIntElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow().elementType().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Integer);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntDefaultElement", "arrayOfIntElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow().elementType().asPrimitiveTypeReference().orElseThrow()).isSameAs(PrimitiveTypeReferences.Integer);
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow().elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Integer.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asPrimitive().orElseThrow().value())
          .toList()).isEqualTo(List.of(1, 2, 3));
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringElement", "arrayOfStringElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asArrayTypeReference().orElseThrow().elementType().asCustomTypeReference().orElseThrow()
          .targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringDefaultElement", "arrayOfStringElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asString().orElseThrow().value())
          .toList()).isEqualTo(List.of("a", "b", "c"));
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumElement", "arrayOfEnumElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumDefaultElement", "arrayOfEnumElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asEnum().orElseThrow().name())
          .toList()).isEqualTo(List.of(TestEnum.Value1.name(), TestEnum.Value3.name()));
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassElement", "arrayOfClassElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassDefaultElement", "arrayOfClassElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asClass().orElseThrow().type().canonicalName())
          .toList()).isEqualTo(List.of(Integer.class.getCanonicalName(), String.class.getCanonicalName()));
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationElement", "arrayOfAnnotationElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationDefaultElement", "arrayOfAnnotationElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayTypeReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      HandleFunctions.handle(elementMethod.defaultValue().orElseThrow().asArray().orElseThrow().elements(), elements -> {
        assertThat(elements).hasSize(1);
        assertThat(elements.get(0).asAnnotation().orElseThrow().annotationStatement().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
        assertThat(elements.get(0).asAnnotation().orElseThrow().elementValue("value").orElseThrow().asString().orElseThrow().value()).isEqualTo("a");
        assertThat(elements.get(0).asAnnotation().orElseThrow().elementValue("otherValue").orElseThrow().asString().orElseThrow().value()).isEqualTo("defaultString");
        assertThat(elementMethod.params()).isEmpty();
      });
    });
  }

  @Test
  public void testAnnotationWithEnumElement() {
    testAnnotationWithElement("AnnotationWithEnumElement", "enumElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithEnumDefaultElement", "enumElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.defaultValue().orElseThrow().asEnum().orElseThrow().name()).isEqualTo(TestEnum.Value2.name());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  private void testAnnotationWithElement(String className, String elementMethodName, Handler<MethodStatement> methodValidator) {
    // Given
    String canonicalClassName = "tech.intellispaces.framework.javastatements.samples." + className;
    TypeElement typeElement = getTestElement("annotation/" + className + ".java");

    // When
    Session session = SessionBuilder.buildSession();
    AnnotationStatement annotationStatement = JavaStatements.annotationStatement(typeElement, session);

    // Then
    assertThat(annotationStatement.simpleName()).isEqualTo(className);
    assertThat(annotationStatement.canonicalName()).isEqualTo(canonicalClassName);

    assertThat(annotationStatement.typeParameters()).isEmpty();

    assertThat(annotationStatement.parentTypes()).hasSize(1);
    assertThat(annotationStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Annotation.class.getCanonicalName());

    assertThat(annotationStatement.annotations()).hasSize(1);
    HandleFunctions.handle(annotationStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(annotationStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly(elementMethodName);

    assertThat(annotationStatement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder(elementMethodName, "hashCode", "annotationType", "equals", "toString");

    List<MethodStatement> methods = annotationStatement.declaredMethodsWithName(elementMethodName);
    assertThat(methods).hasSize(1);
    methodValidator.handle(methods.get(0));

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrder("tech.intellispaces.framework.javastatements.support.TesteeType");

    assertThat(session.getType(canonicalClassName)).isSameAs(annotationStatement);
  }

  @Test
  public void testCyclicAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotation/CyclicAnnotations.java");
    String annotationClassName = "tech.intellispaces.framework.javastatements.samples.CyclicAnnotations.SomeAnnotation";

    // When
    InterfaceStatement interfaceStatement = JavaStatements.interfaceStatement(typeElement);

    // Then
    assertThat(interfaceStatement.annotations()).hasSize(2);
    assertThat(interfaceStatement.selectAnnotation(annotationClassName)).isPresent();

    AnnotationInstance annotation = interfaceStatement.selectAnnotation(annotationClassName).orElseThrow();
    assertThat(annotation.annotationStatement().selectAnnotation(annotationClassName).orElseThrow()
        .annotationStatement().canonicalName()).isEqualTo(annotationClassName);
  }

  @Test
  public void testAnnotationInstance() {
    // Given
    TypeElement typeElement = getTestElement("annotation/AnnotatedClass.java");

    // When
    ClassStatement annotatedClass = JavaStatements.classStatement(typeElement);
    AnnotationWithElementsView annotation = annotatedClass.selectAnnotation(AnnotationWithElementsView.class).orElseThrow();

    // Then
    assertThat(annotation.byteElement()).isEqualTo((byte) 10);
    assertThat(annotation.shortElement()).isEqualTo((short) 11);
    assertThat(annotation.intElement()).isEqualTo(12);
    assertThat(annotation.longElement()).isEqualTo(13);
    assertThat(annotation.floatElement()).isEqualTo(14.0f);
    assertThat(annotation.doubleElement()).isEqualTo(15.0);
    assertThat(annotation.charElement()).isEqualTo('x');
    assertThat(annotation.booleanElement()).isTrue();
    assertThat(annotation.stringElement()).isEqualTo("abcde");
    assertThat(annotation.arrayOfIntElement()).isEqualTo(new int[] { 1, 2, 3, 4 , 5 });
    assertThat(annotation.arrayOfStringElement()).isEqualTo(new String[] { "a", "b", "c", "d", "e" });
    assertThat(annotation.arrayOfEnumElement()).isEqualTo(new TestEnum[] { TestEnum.Value1, TestEnum.Value2, TestEnum.Value3 });
    assertThat(annotation.arrayOfClassElement()).isEqualTo(new Class[] { Object.class, String.class });
    assertThat(annotation.arrayOfAnnotationElement()).hasSize(2)
        .extracting(TestAnnotation::value).containsExactly("abc", "def");
    assertThat(annotation.enumElement()).isEqualTo(TestEnum.Value3);

    assertThat(annotation.byteElementDefault()).isEqualTo((byte) 1);
    assertThat(annotation.shortElementDefault()).isEqualTo((short) 1);
    assertThat(annotation.intElementDefault()).isEqualTo(1);
    assertThat(annotation.longElementDefault()).isEqualTo(1);
    assertThat(annotation.floatElementDefault()).isEqualTo(1.0f);
    assertThat(annotation.doubleElementDefault()).isEqualTo(1.0);
    assertThat(annotation.charElementDefault()).isEqualTo('a');
    assertThat(annotation.booleanElementDefault()).isTrue();
    assertThat(annotation.stringElementDefault()).isEqualTo("abc");
    assertThat(annotation.arrayOfIntElementDefault()).isEqualTo(new int[] { 1, 2, 3 });
    assertThat(annotation.arrayOfStringElementDefault()).isEqualTo(new String[] { "a", "b", "c" });
    assertThat(annotation.arrayOfEnumElementDefault()).isEqualTo(new TestEnum[] { TestEnum.Value1, TestEnum.Value3 });
    assertThat(annotation.arrayOfClassElementDefault()).isEqualTo(new Class[] { Integer.class, String.class });
    assertThat(annotation.arrayOfAnnotationElementDefault()).hasSize(1)
        .extracting(TestAnnotation::value).containsExactly("a");
    assertThat(annotation.enumElementDefault()).isEqualTo(TestEnum.Value2);

    assertThat(annotatedClass.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.framework.javastatements.support.TesteeType"
    );
  }
}
