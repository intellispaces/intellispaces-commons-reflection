package tech.intellispaces.java.reflection;

import tech.intellispaces.java.reflection.customtype.AnnotationType;
import tech.intellispaces.java.reflection.customtype.ClassType;
import tech.intellispaces.java.reflection.customtype.CustomType;
import tech.intellispaces.java.reflection.customtype.InterfaceType;
import tech.intellispaces.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.java.reflection.method.MethodStatement;
import tech.intellispaces.java.reflection.reference.PrimitiveReferences;
import tech.intellispaces.java.reflection.samples.AnnotationWithElementsView;
import tech.intellispaces.java.reflection.samples.TestAnnotation;
import tech.intellispaces.java.reflection.samples.TestEnum;
import tech.intellispaces.java.reflection.session.Session;
import tech.intellispaces.java.reflection.session.Sessions;
import tech.intellispaces.java.reflection.support.TesteeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.intellispaces.general.object.ObjectFunctions;

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
    assertThat(annotationStatement.canonicalName()).isEqualTo("tech.intellispaces.java.reflection.samples.EmptyAnnotation");
    assertThat(annotationStatement.className()).isEqualTo("tech.intellispaces.java.reflection.samples.EmptyAnnotation");

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
        "tech.intellispaces.java.reflection.support.TesteeType"
    );
  }

  @Test
  public void testAnnotationWithByteElement() {
    testAnnotationWithElement("AnnotationWithByteElement", "byteElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithByteDefaultElement() {
    testAnnotationWithElement("AnnotationWithByteDefaultElement", "byteElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Byte);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((byte) 1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortElement() {
    testAnnotationWithElement("AnnotationWithShortElement", "shortElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithShortDefaultElement() {
    testAnnotationWithElement("AnnotationWithShortDefaultElement", "shortElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Short);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo((short) 1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntElement() {
    testAnnotationWithElement("AnnotationWithIntElement", "intElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithIntDefaultElement", "intElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongElement() {
    testAnnotationWithElement("AnnotationWithLongElement", "longElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithLongDefaultElement() {
    testAnnotationWithElement("AnnotationWithLongDefaultElement", "longElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Long);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1L);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatElement() {
    testAnnotationWithElement("AnnotationWithFloatElement", "floatElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithFloatDefaultElement() {
    testAnnotationWithElement("AnnotationWithFloatDefaultElement", "floatElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Float);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0f);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleElement() {
    testAnnotationWithElement("AnnotationWithDoubleElement", "doubleElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithDoubleDefaultElement() {
    testAnnotationWithElement("AnnotationWithDoubleDefaultElement", "doubleElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Double);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(1.0);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharElement() {
    testAnnotationWithElement("AnnotationWithCharElement", "charElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithCharDefaultElement() {
    testAnnotationWithElement("AnnotationWithCharDefaultElement", "charElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Char);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo('a');
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanElement() {
    testAnnotationWithElement("AnnotationWithBooleanElement", "booleanElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithBooleanDefaultElement() {
    testAnnotationWithElement("AnnotationWithBooleanDefaultElement", "booleanElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().type()).isSameAs(PrimitiveReferences.Boolean);
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asPrimitive().orElseThrow().value()).isEqualTo(true);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringElement() {
    testAnnotationWithElement("AnnotationWithStringElement", "stringElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithStringDefaultElement", "stringElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(
          String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().typeArguments()).isEmpty();
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asString().orElseThrow().value()).isEqualTo("abc");
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntElement", "arrayOfIntElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow().elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfIntDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfIntDefaultElement", "arrayOfIntElementDefault", elementMethod -> {
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
    testAnnotationWithElement("AnnotationWithArrayOfStringElement", "arrayOfStringElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asArrayReference().orElseThrow().elementType().asCustomTypeReference().orElseThrow()
          .targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfStringDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfStringDefaultElement", "arrayOfStringElementDefault", elementMethod -> {
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
    testAnnotationWithElement("AnnotationWithArrayOfEnumElement", "arrayOfEnumElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfEnumDefaultElement", "arrayOfEnumElementDefault", elementMethod -> {
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
    testAnnotationWithElement("AnnotationWithArrayOfClassElement", "arrayOfClassElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Class.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfClassDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfClassDefaultElement", "arrayOfClassElementDefault", elementMethod -> {
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
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationElement", "arrayOfAnnotationElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow()
          .asArrayReference().orElseThrow()
          .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestAnnotation.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithArrayOfAnnotationDefaultElement() {
    testAnnotationWithElement("AnnotationWithArrayOfAnnotationDefaultElement", "arrayOfAnnotationElementDefault", elementMethod -> {
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
    testAnnotationWithElement("AnnotationWithEnumElement", "enumElement", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  @Test
  public void testAnnotationWithEnumDefaultElement() {
    testAnnotationWithElement("AnnotationWithEnumDefaultElement", "enumElementDefault", elementMethod -> {
      Assertions.assertThat(elementMethod.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
      Assertions.assertThat(elementMethod.defaultValue().orElseThrow().asEnum().orElseThrow().name()).isEqualTo(TestEnum.Value2.name());
      Assertions.assertThat(elementMethod.params()).isEmpty();
    });
  }

  private void testAnnotationWithElement(String className, String elementMethodName, Consumer<MethodStatement> methodValidator) {
    // Given
    String canonicalClassName = "tech.intellispaces.java.reflection.samples." + className;
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

    assertThat(annotationStatement.dependencyTypenames()).containsExactlyInAnyOrder("tech.intellispaces.java.reflection.support.TesteeType");

    assertThat(session.getType(canonicalClassName)).isSameAs(annotationStatement);
  }

  @Test
  public void testCyclicAnnotation() {
    // Given
    TypeElement typeElement = getTestElement("annotations/CyclicAnnotations.java");
    String annotationClassName = "tech.intellispaces.java.reflection.samples.CyclicAnnotations.SomeAnnotation";

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
        "tech.intellispaces.java.reflection.support.TesteeType"
    );
  }
}
