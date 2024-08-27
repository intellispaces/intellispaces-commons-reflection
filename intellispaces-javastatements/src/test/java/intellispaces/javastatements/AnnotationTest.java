package intellispaces.javastatements;

import intellispaces.commons.datahandle.HandleFunctions;
import intellispaces.javastatements.customtype.AnnotationType;
import intellispaces.javastatements.customtype.ClassType;
import intellispaces.javastatements.customtype.CustomType;
import intellispaces.javastatements.customtype.InterfaceType;
import intellispaces.javastatements.instance.AnnotationInstance;
import intellispaces.javastatements.method.MethodStatement;
import intellispaces.javastatements.reference.PrimitiveReferences;
import intellispaces.javastatements.samples.AnnotationWithElementsView;
import intellispaces.javastatements.samples.TestAnnotation;
import intellispaces.javastatements.samples.TestEnum;
import intellispaces.javastatements.session.Session;
import intellispaces.javastatements.session.Sessions;
import intellispaces.javastatements.support.TesteeType;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AnnotationType}.
 */
public class AnnotationTest extends AbstractCustomStatementTest {

  @Test
  public void testEmptyAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotations/EmptyAnnotation.java");

    // When
    CustomType customStatementType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customStatementType).isInstanceOf(AnnotationType.class);
    AnnotationType annotationStatement = customStatementType.asAnnotation().orElse(null);
    assertThat(annotationStatement).isNotNull();

    assertThat(annotationStatement.isAbstract()).isTrue();
    assertThat(annotationStatement.simpleName()).isEqualTo("EmptyAnnotation");
    assertThat(annotationStatement.canonicalName()).isEqualTo("intellispaces.javastatements.samples.EmptyAnnotation");
    assertThat(annotationStatement.className()).isEqualTo("intellispaces.javastatements.samples.EmptyAnnotation");

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
        "intellispaces.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testAnnotationWithByteElement() {
    testAnnotationWithElement("AnnotationWithByteElement", "byteElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithByteDefaultElement() {
    testAnnotationWithElement("AnnotationWithByteDefaultElement", "byteElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Byte);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((byte) 1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortElement() {
    testAnnotationWithElement("AnnotationWithShortElement", "shortElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortDefaultElement() {
    testAnnotationWithElement("AnnotationWithShortDefaultElement", "shortElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Short);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((short) 1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntElement() {
    testAnnotationWithElement("AnnotationWithIntElement", "intElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Integer);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithIntDefaultElement", "intElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Integer);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Integer);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongElement() {
    testAnnotationWithElement("AnnotationWithLongElement", "longElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongDefaultElement() {
    testAnnotationWithElement("AnnotationWithLongDefaultElement", "longElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Long);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1L);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatElement() {
    testAnnotationWithElement("AnnotationWithFloatElement", "floatElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatDefaultElement() {
    testAnnotationWithElement("AnnotationWithFloatDefaultElement", "floatElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Float);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0f);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleElement() {
    testAnnotationWithElement("AnnotationWithDoubleElement", "doubleElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleDefaultElement() {
    testAnnotationWithElement("AnnotationWithDoubleDefaultElement", "doubleElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Double);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharElement() {
    testAnnotationWithElement("AnnotationWithCharElement", "charElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharDefaultElement() {
    testAnnotationWithElement("AnnotationWithCharDefaultElement", "charElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Char);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo('a');
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanElement() {
    testAnnotationWithElement("AnnotationWithBooleanElement", "booleanElement", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanDefaultElement() {
    testAnnotationWithElement("AnnotationWithBooleanDefaultElement", "booleanElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Boolean);
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
          .asArrayReference().orElseThrow().elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Integer);
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntDefaultElement", "arrayOfIntElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow().elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Integer);
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
      assertThat(elementMethod.returnType().orElseThrow().asArrayReference().orElseThrow().elementType().asCustomTypeReference().orElseThrow()
          .targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringDefaultElement", "arrayOfStringElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
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
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumDefaultElement", "arrayOfEnumElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
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
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassDefaultElement", "arrayOfClassElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
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
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationDefaultElement", "arrayOfAnnotationElementDefault", elementMethod -> {
      assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
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

  private void testAnnotationWithElement(String className, String elementMethodName, Consumer<MethodStatement> methodValidator) {
    // Given
    String canonicalClassName = "intellispaces.javastatements.samples." + className;
    TypeElement typeElement = getTestElement("annotations/" + className + ".java");

    // When
    Session session = Sessions.get();
    AnnotationType annotationStatement = JavaStatements.annotationStatement(typeElement, session);

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
    methodValidator.accept(methods.get(0));

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrder("intellispaces.javastatements.support.TesteeType");

    assertThat(session.getType(canonicalClassName)).isSameAs(annotationStatement);
  }

  @Test
  public void testCyclicAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotations/CyclicAnnotations.java");
    String annotationClassName = "intellispaces.javastatements.samples.CyclicAnnotations.SomeAnnotation";

    // When
    InterfaceType interfaceStatement = JavaStatements.interfaceStatement(typeElement);

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
    TypeElement typeElement = getTestElement("annotations/AnnotatedClass.java");

    // When
    ClassType annotatedClass = JavaStatements.classStatement(typeElement);
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
        "intellispaces.javastatements.support.TesteeType"
    );
  }
}
