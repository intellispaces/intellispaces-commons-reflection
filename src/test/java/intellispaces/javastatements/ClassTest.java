package intellispaces.javastatements;

import intellispaces.commons.function.CommonFunctions;
import intellispaces.commons.function.CollectionFunctions;
import intellispaces.commons.model.action.HandlerAction;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.session.SessionFactory;
import intellispaces.javastatements.support.TesteeType;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link intellispaces.javastatements.model.custom.ClassStatement}.
 */
public class ClassTest extends AbstractCustomTypeTest {

  @Test
  public void testEmptyClass() {
    // Given
    TypeElement typeElement = getTestElement("class/EmptyClass.java");

    // When
    CustomType customTypeStatement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customTypeStatement).isInstanceOf(ClassStatement.class);
    ClassStatement classStatement = customTypeStatement.asClass().orElse(null);
    assertThat(classStatement).isNotNull();

    assertThat(classStatement.simpleName()).isEqualTo("EmptyClass");
    assertThat(classStatement.canonicalName()).isEqualTo("intellispaces.javastatements.sample.EmptyClass");

    assertThat(classStatement.typeParameters()).isEmpty();
    assertThat(classStatement.extendedClass()).isEmpty();
    assertThat(classStatement.implementedInterfaces()).isEmpty();
    assertThat(classStatement.parentTypes()).isEmpty();
    assertThat(classStatement.declaredMethods()).isEmpty();

    assertThat(classStatement.annotations()).hasSize(1);
    CommonFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "intellispaces.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testClassExtendedSuperClassAndImplementedTwoInterfaces() {
    // Given
    final var superClassName = "intellispaces.javastatements.sample.ClassExtendedSuperClassAndImplementedTwoInterfaces.SuperClass";
    final var interface1Name = "intellispaces.javastatements.sample.ClassExtendedSuperClassAndImplementedTwoInterfaces.Interface1";
    final var interface2Name = "intellispaces.javastatements.sample.ClassExtendedSuperClassAndImplementedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("class/ClassExtendedSuperClassAndImplementedTwoInterfaces.java");

    // When
    ClassStatement classStatement = JavaStatements.classStatement(typeElement);

    // Then
    assertThat(classStatement).isNotNull();

    assertThat(classStatement.simpleName()).isEqualTo("TesteeClass");
    assertThat(classStatement.canonicalName()).isEqualTo("intellispaces.javastatements.sample.ClassExtendedSuperClassAndImplementedTwoInterfaces.TesteeClass");

    assertThat(classStatement.typeParameters()).isEmpty();

    assertThat(classStatement.extendedClass()).isPresent();
    CommonFunctions.handle(classStatement.extendedClass().orElseThrow(), extendedClass -> {
      assertThat(extendedClass.targetType().canonicalName()).isEqualTo(superClassName);
      assertThat(extendedClass.typeArguments()).isEmpty();
    });

    assertThat(classStatement.implementedInterfaces()).hasSize(2);
    CommonFunctions.handle(classStatement.implementedInterfaces().get(0), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });
    CommonFunctions.handle(classStatement.implementedInterfaces().get(1), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });

    assertThat(classStatement.parentTypes()).hasSize(3);
    CommonFunctions.handle(classStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(superClassName);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    CommonFunctions.handle(classStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    CommonFunctions.handle(classStatement.parentTypes().get(2), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(classStatement.declaredMethods()).isEmpty();

    assertThat(classStatement.annotations()).hasSize(1);
    CommonFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "intellispaces.javastatements.support.TesteeType"
    );

    assertThat(classStatement.extendedClass().orElseThrow().referenceDeclaration()).isEqualTo("SuperClass");
    assertThat(classStatement.implementedInterfaces().get(0).referenceDeclaration()).isEqualTo("Interface1");
    assertThat(classStatement.implementedInterfaces().get(1).referenceDeclaration()).isEqualTo("Interface2");
  }

  @Test
  public void testClassWithSimpleMethod() {
    testClassWithOneMethod("ClassWithSimpleMethod", "simpleMethod", this::validateSimpleMethod, List.of());
  }

  @Test
  public void testClassWithMethodThrowsTwoExceptions() {
    testClassWithOneMethod("ClassWithMethodThrowsTwoExceptions", "methodThrowsTwoExceptions", this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName()));
  }

  @Test
  public void testClassWithStaticMethod() {
    testClassWithOneMethod("ClassWithStaticMethod", "staticMethod", this::validateStaticMethod, List.of());
  }

  @Test
  public void testClassWithMethodUsingLocalTypeParameter() {
    testClassWithOneMethod("ClassWithMethodUsingLocalTypeParameter", "methodUsingLocalTypeParameter", this::validateMethodUsingLocalTypeParameter,
        List.of(List.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcard() {
    testClassWithOneMethod("ClassWithMethodUsingWildcard", "methodUsingWildcard", this::validateMethodUsingWildcard,
        List.of(List.class.getCanonicalName(), Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcardThatExtendsOtherClass() {
    testClassWithOneMethod("ClassWithMethodUsingWildcardThatExtendsOtherClass", "methodUsingWildcardThatExtendsOtherClass", this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcardThatSuperOtherClass() {
    testClassWithOneMethod("ClassWithMethodUsingWildcardThatSuperOtherClass", "methodUsingWildcardThatSuperOtherClass", this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithByteGetter() {
    testClassWithOneMethod("ClassWithByteGetter", "byteGetter", this::validateByteGetter, List.of());
  }

  @Test
  public void testClassWithShortGetter() {
    testClassWithOneMethod("ClassWithShortGetter", "shortGetter", this::validateShortGetter, List.of());
  }

  @Test
  public void testClassWithIntGetter() {
    testClassWithOneMethod("ClassWithIntGetter", "intGetter", this::validateIntGetter, List.of());
  }

  @Test
  public void testClassWithLongGetter() {
    testClassWithOneMethod("ClassWithLongGetter", "longGetter", this::validateLongGetter, List.of());
  }

  @Test
  public void testClassWithFloatGetter() {
    testClassWithOneMethod("ClassWithFloatGetter", "floatGetter", this::validateFloatGetter, List.of());
  }

  @Test
  public void testClassWithDoubleGetter() {
    testClassWithOneMethod("ClassWithDoubleGetter", "doubleGetter", this::validateDoubleGetter, List.of());
  }

  @Test
  public void testClassWithCharGetter() {
    testClassWithOneMethod("ClassWithCharGetter", "charGetter", this::validateCharGetter, List.of());
  }

  @Test
  public void testClassWithBooleanGetter() {
    testClassWithOneMethod("ClassWithBooleanGetter", "booleanGetter", this::validateBooleanGetter, List.of());
  }

  @Test
  public void testClassWithStringGetter() {
    testClassWithOneMethod("ClassWithStringGetter", "stringGetter", this::validateStringGetter, List.of());
  }

  @Test
  public void testClassWithArrayOfIntGetter() {
    testClassWithOneMethod("ClassWithArrayOfIntGetter", "arrayOfIntGetter", this::validateArrayOfIntGetter, List.of());
  }

  @Test
  public void testClassWithDoubleArrayOfStringGetter() {
    testClassWithOneMethod("ClassWithDoubleArrayOfStringGetter", "doubleArrayOfStringGetter", this::validateDoubleArrayOfStringGetter, List.of());
  }

  @Test
  public void testClassWithEnumGetter() {
    testClassWithOneMethod("ClassWithEnumGetter", "enumGetter", this::validateEnumGetter, List.of());
  }

  @Test
  public void testClassWithRecordGetter() {
    testClassWithOneMethod("ClassWithRecordGetter", "recordGetter", this::validateRecordGetter, List.of());
  }

  @Test
  public void testClassWithInheritedMethodFromExtendedClass() {
    // Given
    testCustomTypeWithInheritedMethod("class/ClassWithInheritedMethodFromExtendedClass.java");
  }

  @Test
  public void testClassWithImplementedMethodFromInterface() {
    testCustomTypeWithImplementedMethodFromInterface("class/ClassWithImplementedMethodFromInterface.java", List.of(), List.of());
  }

  @Test
  public void testClassWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface("class/ClassWithInheritedDefaultMethodFromInterface.java", List.of(), List.of());
  }

  @Test
  public void testClassWithImplementedMethod() {
    testCustomerTypeWithOverrideMethod("class/ClassWithImplementedMethod.java", List.of(), List.of());
  }

  @Test
  public void testClassWithOverrideMethod() {
    testCustomerTypeWithOverrideMethod("class/ClassWithOverrideMethod.java", List.of(), List.of());
  }

  @Test
  public void testClassWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType("class/ClassWithOverrideMethodAndNarrowedReturnType.java", List.of(), List.of());
  }

  @Test
  public void testGenericClassWithOneTypeParameter() {
    // Given
    TypeElement typeElement = getTestElement("class/GenericClassWithOneTypeParameter.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("GenericClassWithOneTypeParameter");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("GenericClassWithOneTypeParameter<T>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("GenericClassWithOneTypeParameter<T>");

    assertThat(typeReference.targetType().asClass()).isPresent();
    ClassStatement classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(1);
    CommonFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).isEmpty();
    });

    assertThat(classStatement.declaredMethods()).hasSize(1);
    CommonFunctions.handle(classStatement.declaredMethods().get(0), method -> {
      assertThat(method.name()).isEqualTo("process");
      assertThat(method.params()).hasSize(1);
      assertThat(method.params().get(0).name()).isEqualTo("arg");
      assertThat(method.params().get(0).type().asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
      assertThat(method.returnType().orElseThrow().asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
    });
  }

  @Test
  public void testGenericClassWithMultipleTypeParameters() {
    // Given
    TypeElement typeElement = getTestElement("class/GenericClassWithMultipleTypeParameters.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters<T1, T2 extends T1, T3 extends Number, T4 extends AutoCloseable & DataInput>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters<T1, T2, T3, T4>");

    assertThat(typeReference.targetType().asClass()).isPresent();
    ClassStatement classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(4);
    CommonFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T1");
      assertThat(typeParam.extendedBounds()).isEmpty();
      assertThat(typeParam.referenceDeclaration()).isEqualTo("T1");
      assertThat(typeParam.typeFullDeclaration()).isEqualTo("T1");
      assertThat(typeParam.typeBriefDeclaration()).isEqualTo("T1");
    });
    CommonFunctions.handle(classStatement.typeParameters().get(1), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T2");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
      assertThat(typeParam.referenceDeclaration()).isEqualTo("T2");
      assertThat(typeParam.typeFullDeclaration()).isEqualTo("T2 extends T1");
      assertThat(typeParam.typeBriefDeclaration()).isEqualTo("T2");
    });
    CommonFunctions.handle(classStatement.typeParameters().get(2), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T3");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Number.class.getCanonicalName());
      assertThat(typeParam.referenceDeclaration()).isEqualTo("T3");
      assertThat(typeParam.typeFullDeclaration()).isEqualTo("T3 extends Number");
      assertThat(typeParam.typeBriefDeclaration()).isEqualTo("T3");
    });

    CommonFunctions.handle(classStatement.typeParameters().get(3), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T4");
      assertThat(typeParam.extendedBounds()).hasSize(2);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(AutoCloseable.class.getCanonicalName());
      assertThat(typeParam.extendedBounds().get(1).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(DataInput.class.getCanonicalName());
      assertThat(typeParam.referenceDeclaration()).isEqualTo("T4");
      assertThat(typeParam.typeFullDeclaration()).isEqualTo("T4 extends AutoCloseable & DataInput");
      assertThat(typeParam.typeBriefDeclaration()).isEqualTo("T4");
    });

    assertThat(classStatement.declaredMethods()).hasSize(1);
    CommonFunctions.handle(classStatement.declaredMethods().get(0), method -> {
      assertThat(method.name()).isEqualTo("process");
      assertThat(method.params()).hasSize(2);

      assertThat(method.params().get(0).name()).isEqualTo("arg1");
      assertThat(method.params().get(0).type().asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));

      assertThat(method.params().get(1).name()).isEqualTo("arg2");
      assertThat(method.params().get(1).type().asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(2));

      assertThat(method.returnType().orElseThrow().asNamedTypeReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(1));
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "intellispaces.javastatements.support.TesteeType",
        "java.io.DataInput"
    );
  }

  @Test
  public void testGenericClassWithCyclicTypeDependencyCase1() {
    // Given
    TypeElement typeElement = getTestElement("class/GenericClassWithCyclicTypeDependencyCase1.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1<T>");

    assertThat(typeReference.targetType().asClass()).isPresent();
    ClassStatement classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(1);
    CommonFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(classStatement);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments().get(0)
          .asNamedTypeReference().orElseThrow().name()).isEqualTo("T");
    });
  }

  @Test
  public void testGenericClassWithCyclicTypeDependencyCase2() {
    // Given
    TypeElement typeElement = getTestElement("class/GenericClassWithCyclicTypeDependencyCase2.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("ClassA");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("ClassA<T1 extends ClassB<?>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("ClassA<T1>");

    assertThat(typeReference.targetType().asClass()).isPresent();
    ClassStatement classAStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classAStatement.typeParameters()).hasSize(1);
    CommonFunctions.handle(classAStatement.typeParameters().get(0), classATypeParam -> {
      assertThat(classATypeParam.name()).isEqualTo("T1");
      assertThat(classATypeParam.extendedBounds()).hasSize(1);
      CommonFunctions.handle(classATypeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType(), classBExtendedBound -> {
        assertThat(classBExtendedBound.canonicalName()).isEqualTo("intellispaces.javastatements.sample.GenericClassWithCyclicTypeDependencyCase2.ClassB");
        assertThat(classBExtendedBound.typeParameters()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().name()).isEqualTo("T2");
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds().get(0)
            .asCustomTypeReference().orElseThrow().targetType()).isSameAs(classAStatement);
      });
    });
  }

  private void testClassWithOneMethod(
      String className, String methodName, HandlerAction<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String canonicalClassName = "intellispaces.javastatements.sample." + className;
    TypeElement typeElement = getTestElement("class/" + className + ".java");
    Session session = SessionFactory.create();

    // When
    ClassStatement classStatement = JavaStatements.classStatement(typeElement, session);

    // Then
    assertThat(classStatement.simpleName()).isEqualTo(className);
    assertThat(classStatement.canonicalName()).isEqualTo(canonicalClassName);

    assertThat(classStatement.typeParameters()).isEmpty();
    assertThat(classStatement.extendedClass()).isEmpty();
    assertThat(classStatement.implementedInterfaces()).isEmpty();
    assertThat(classStatement.parentTypes()).isEmpty();

    assertThat(classStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly(methodName);

    assertThat(classStatement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly(methodName);

    List<MethodStatement> declaredMethods = classStatement.declaredMethodsWithName(methodName);
    assertThat(declaredMethods).hasSize(1);
    methodValidator.execute(declaredMethods.get(0));

    List<MethodStatement> actualMethods = classStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.execute(actualMethods.get(0));

    assertThat(classStatement.annotations()).hasSize(1);
    CommonFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports, "intellispaces.javastatements.support.TesteeType")
    );

    assertThat(session.getType(canonicalClassName)).isSameAs(classStatement);
  }
}
