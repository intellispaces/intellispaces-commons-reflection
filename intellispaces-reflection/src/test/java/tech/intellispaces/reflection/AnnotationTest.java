package tech.intellispaces.reflection;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import tech.intellispaces.commons.collection.CollectionFunctions;
import tech.intellispaces.commons.object.ObjectFunctions;
import tech.intellispaces.reflection.customtype.AnnotationType;
import tech.intellispaces.reflection.customtype.ClassType;
import tech.intellispaces.reflection.customtype.CustomType;
import tech.intellispaces.reflection.customtype.InterfaceType;
import tech.intellispaces.reflection.instance.AnnotationInstance;
import tech.intellispaces.reflection.method.MethodStatement;
import tech.intellispaces.reflection.reference.PrimitiveReferences;
import tech.intellispaces.reflection.samples.AnnotationWithElementsView;
import tech.intellispaces.reflection.samples.TestAnnotation;
import tech.intellispaces.reflection.samples.TestEnum;
import tech.intellispaces.reflection.session.Session;
import tech.intellispaces.reflection.session.Sessions;
import tech.intellispaces.reflection.support.TesteeType;

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
    assertThat(annotationStatement.canonicalName()).isEqualTo("tech.intellispaces.reflection.samples.EmptyAnnotation");
    assertThat(annotationStatement.className()).isEqualTo("tech.intellispaces.reflection.samples.EmptyAnnotation");

    assertThat(annotationStatement.typeParameters()).isEmpty();

    Assertions.assertThat(annotationStatement.parentTypes()).hasSize(1);
    Assertions.assertThat(annotationStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Annotation.class.getCanonicalName());

    Assertions.assertThat(annotationStatement.declaredMethods()).isEmpty();

    Assertions.assertThat(annotationStatement.annotations()).hasSize(1);
    ObjectFunctions.handle(annotationStatement.annotations().get(0), annInstance -> {
      Assertions.assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      Assertions.assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.reflection.support.TesteeType"
    );
  }

  @Test
  public void testAnnotationWithByteElement() {
    testAnnotationWithElement("AnnotationWithByteElement", "byteElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithByteDefaultElement() {
    testAnnotationWithElement("AnnotationWithByteDefaultElement", "byteElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((byte) 1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortElement() {
    testAnnotationWithElement("AnnotationWithShortElement", "shortElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortDefaultElement() {
    testAnnotationWithElement("AnnotationWithShortDefaultElement", "shortElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((short) 1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntElement() {
    testAnnotationWithElement("AnnotationWithIntElement", "intElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithIntDefaultElement", "intElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongElement() {
    testAnnotationWithElement("AnnotationWithLongElement", "longElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongDefaultElement() {
    testAnnotationWithElement("AnnotationWithLongDefaultElement", "longElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1L);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatElement() {
    testAnnotationWithElement("AnnotationWithFloatElement", "floatElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatDefaultElement() {
    testAnnotationWithElement("AnnotationWithFloatDefaultElement", "floatElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0f);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleElement() {
    testAnnotationWithElement("AnnotationWithDoubleElement", "doubleElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleDefaultElement() {
    testAnnotationWithElement("AnnotationWithDoubleDefaultElement", "doubleElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharElement() {
    testAnnotationWithElement("AnnotationWithCharElement", "charElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharDefaultElement() {
    testAnnotationWithElement("AnnotationWithCharDefaultElement", "charElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo('a');
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanElement() {
    testAnnotationWithElement("AnnotationWithBooleanElement", "booleanElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanDefaultElement() {
    testAnnotationWithElement("AnnotationWithBooleanDefaultElement", "booleanElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(true);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringElement() {
    testAnnotationWithElement("AnnotationWithStringElement", "stringElement",
            List.of(String.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithStringDefaultElement", "stringElementDefault",
            List.of(String.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().typeArguments()).isEmpty();
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asString().orElseThrow().value()).isEqualTo("abc");
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntElement", "arrayOfIntElement",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow().elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntDefaultElement", "arrayOfIntElementDefault",
            List.of(),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow().elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow().elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Integer.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asPrimitive().orElseThrow().value())
          .toList()).isEqualTo(List.of(1, 2, 3));
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringElement", "arrayOfStringElement",
            List.of(String.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asArrayReference().orElseThrow().elementType().asCustomTypeReference().orElseThrow()
          .targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringDefaultElement", "arrayOfStringElementDefault",
            List.of(String.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asString().orElseThrow().value())
          .toList()).isEqualTo(List.of("a", "b", "c"));
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumElement", "arrayOfEnumElement",
            List.of("tech.intellispaces.reflection.samples.TestEnum"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumDefaultElement", "arrayOfEnumElementDefault",
            List.of("tech.intellispaces.reflection.samples.TestEnum"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asEnum().orElseThrow().name())
          .toList()).isEqualTo(List.of(TestEnum.Value1.name(), TestEnum.Value3.name()));
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassElement", "arrayOfClassElement",
            List.of(Class.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassDefaultElement", "arrayOfClassElementDefault",
            List.of(Class.class.getCanonicalName()),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elements().stream()
          .map(instance -> instance.asClass().orElseThrow().type().canonicalName())
          .toList()).isEqualTo(List.of(Integer.class.getCanonicalName(), String.class.getCanonicalName()));
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationElement", "arrayOfAnnotationElement",
            List.of("tech.intellispaces.reflection.samples.TestAnnotation"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationDefaultElement", "arrayOfAnnotationElementDefault",
            List.of("tech.intellispaces.reflection.samples.TestAnnotation"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow()
          .asArray().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      ObjectFunctions.handle(elementMethod.defaultValue().orElseThrow().asArray().orElseThrow().elements(), elements -> {
        Assertions.assertThat(elements).hasSize(1);
        Assertions.assertThat(elements.get(0).asAnnotation().orElseThrow().annotationStatement().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
        Assertions.assertThat(elements.get(0).asAnnotation().orElseThrow().value().orElseThrow().asString().orElseThrow().value()).isEqualTo("a");
        Assertions.assertThat(elements.get(0).asAnnotation().orElseThrow().valueOf("otherValue").orElseThrow().asString().orElseThrow().value()).isEqualTo("defaultString");
        Assertions.assertThat(elementMethod.params()).isEmpty();
      });
    });
  }

  @Test
  public void testAnnotationWithEnumElement() {
    testAnnotationWithElement("AnnotationWithEnumElement", "enumElement",
            List.of("tech.intellispaces.reflection.samples.TestEnum"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithEnumDefaultElement", "enumElementDefault",
            List.of("tech.intellispaces.reflection.samples.TestEnum"),
            elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asEnum().orElseThrow().name()).isEqualTo(TestEnum.Value2.name());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  private void testAnnotationWithElement(
          String className, String elementMethodName, List<String> dependencies, Consumer<MethodStatement> methodValidator
  ) {
    // Given
    String canonicalClassName = "tech.intellispaces.reflection.samples." + className;
    TypeElement typeElement = getTestElement("annotations/" + className + ".java");

    // When
    Session session = Sessions.get();
    AnnotationType annotationStatement = JavaStatements.annotationStatement(typeElement, session);

    // Then
    assertThat(annotationStatement.simpleName()).isEqualTo(className);
    assertThat(annotationStatement.canonicalName()).isEqualTo(canonicalClassName);

    assertThat(annotationStatement.typeParameters()).isEmpty();

    Assertions.assertThat(annotationStatement.parentTypes()).hasSize(1);
    Assertions.assertThat(annotationStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Annotation.class.getCanonicalName());

    Assertions.assertThat(annotationStatement.annotations()).hasSize(1);
    ObjectFunctions.handle(annotationStatement.annotations().get(0), annInstance -> {
      Assertions.assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      Assertions.assertThat(annInstance.elements()).isEmpty();
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

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
            CollectionFunctions.join(dependencies, "tech.intellispaces.reflection.support.TesteeType"));

    assertThat(session.getType(canonicalClassName)).isSameAs(annotationStatement);
  }

  @Test
  public void testCyclicAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotations/CyclicAnnotations.java");
    String annotationClassName = "tech.intellispaces.reflection.samples.CyclicAnnotations.SomeAnnotation";

    // When
    InterfaceType interfaceStatement = JavaStatements.interfaceStatement(typeElement);

    // Then
    Assertions.assertThat(interfaceStatement.annotations()).hasSize(2);
    Assertions.assertThat(interfaceStatement.selectAnnotation(annotationClassName)).isPresent();

    AnnotationInstance annotation = interfaceStatement.selectAnnotation(annotationClassName).orElseThrow();
    Assertions.assertThat(annotation.annotationStatement().selectAnnotation(annotationClassName).orElseThrow()
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
        "tech.intellispaces.reflection.support.TesteeType",
        "tech.intellispaces.reflection.samples.TestEnum",
        TestAnnotation.class.getCanonicalName(),
        AnnotationWithElementsView.class.getCanonicalName(),
        String.class.getCanonicalName(),
        Class.class.getCanonicalName(),     // todo: It should be excluded, since it is the default value of an undefined attribute
        Integer.class.getCanonicalName()    // todo: It should be excluded, since it is the default value of an undefined attribute
    );
  }
}
