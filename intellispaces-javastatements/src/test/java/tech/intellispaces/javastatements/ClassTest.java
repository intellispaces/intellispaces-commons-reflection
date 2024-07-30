package tech.intellispaces.javastatements;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.intellispaces.commons.collection.CollectionFunctions;
import tech.intellispaces.commons.datahandle.HandleFunctions;
import tech.intellispaces.javastatements.customtype.ClassType;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.method.MethodStatement;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.session.Sessions;
import tech.intellispaces.javastatements.support.TesteeType;

import javax.lang.model.element.TypeElement;
import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ClassType}.
 */
public class ClassTest extends AbstractCustomStatementTest {

  @Test
  public void testEmptyClass() {
    // Given
    TypeElement typeElement = getTestElement("classes/EmptyClass.java");

    // When
    CustomType customStatementType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customStatementType).isInstanceOf(ClassType.class);
    ClassType classStatement = customStatementType.asClass().orElse(null);
    assertThat(classStatement).isNotNull();

    assertThat(classStatement.isNested()).isFalse();
    assertThat(classStatement.isAbstract()).isFalse();
    assertThat(classStatement.simpleName()).isEqualTo("EmptyClass");
    assertThat(classStatement.canonicalName()).isEqualTo("tech.intellispaces.javastatements.samples.EmptyClass");
    assertThat(classStatement.className()).isEqualTo("tech.intellispaces.javastatements.samples.EmptyClass");

    assertThat(classStatement.typeParameters()).isEmpty();
    assertThat(classStatement.extendedClass()).isEmpty();
    assertThat(classStatement.implementedInterfaces()).isEmpty();
    assertThat(classStatement.parentTypes()).isEmpty();
    assertThat(classStatement.constructors()).hasSize(1);
    validateDefaultConstructor(classStatement.constructors().get(0));
    assertThat(classStatement.declaredMethods()).isEmpty();

    assertThat(classStatement.annotations()).hasSize(1);
    HandleFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testAbstractClass() {
    // Given
    TypeElement typeElement = getTestElement("classes/AbstractClass.java");

    // When
    CustomType customStatementType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customStatementType).isInstanceOf(ClassType.class);
    ClassType classStatement = customStatementType.asClass().orElse(null);
    assertThat(classStatement).isNotNull();

    assertThat(classStatement.isAbstract()).isTrue();

    assertThat(classStatement.declaredMethods()).hasSize(1);
    assertThat(classStatement.declaredMethods().get(0).isAbstract()).isTrue();
  }

  @Test
  public void testNestedClass() {
    // Given
    TypeElement typeElement = getTestElement("classes/NestedClass.java");

    // When
    ClassType classStatement = JavaStatements.classStatement(typeElement);

    // Then
    assertThat(classStatement.isNested()).isTrue();
    assertThat(classStatement.simpleName()).isEqualTo("NestedClass2");
    assertThat(classStatement.canonicalName()).isEqualTo("tech.intellispaces.javastatements.samples.NestedClass.NestedClass1.NestedClass2");
    assertThat(classStatement.className()).isEqualTo("tech.intellispaces.javastatements.samples.NestedClass$NestedClass1$NestedClass2");
  }

  @Test
  public void testClassExtendedSuperClassAndImplementedTwoInterfaces() {
    // Given
    final var superClassName = "tech.intellispaces.javastatements.samples.ClassExtendedSuperClassAndImplementedTwoInterfaces.SuperClass";
    final var interface1Name = "tech.intellispaces.javastatements.samples.ClassExtendedSuperClassAndImplementedTwoInterfaces.Interface1";
    final var interface2Name = "tech.intellispaces.javastatements.samples.ClassExtendedSuperClassAndImplementedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("classes/ClassExtendedSuperClassAndImplementedTwoInterfaces.java");

    // When
    ClassType classStatement = JavaStatements.classStatement(typeElement);

    // Then
    assertThat(classStatement).isNotNull();

    assertThat(classStatement.simpleName()).isEqualTo("TesteeClass");
    assertThat(classStatement.canonicalName()).isEqualTo("tech.intellispaces.javastatements.samples.ClassExtendedSuperClassAndImplementedTwoInterfaces.TesteeClass");
    assertThat(classStatement.className()).isEqualTo("tech.intellispaces.javastatements.samples.ClassExtendedSuperClassAndImplementedTwoInterfaces$TesteeClass");

    assertThat(classStatement.hasParent(superClassName)).isTrue();
    assertThat(classStatement.hasParent(interface1Name)).isTrue();
    assertThat(classStatement.hasParent(interface2Name)).isTrue();
    assertThat(classStatement.hasParent(Object.class.getCanonicalName())).isFalse();

    assertThat(classStatement.typeParameters()).isEmpty();

    assertThat(classStatement.extendedClass()).isPresent();
    HandleFunctions.handle(classStatement.extendedClass().orElseThrow(), extendedClass -> {
      assertThat(extendedClass.targetType().canonicalName()).isEqualTo(superClassName);
      assertThat(extendedClass.typeArguments()).isEmpty();
    });

    assertThat(classStatement.implementedInterfaces()).hasSize(2);
    HandleFunctions.handle(classStatement.implementedInterfaces().get(0), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(classStatement.implementedInterfaces().get(1), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });

    assertThat(classStatement.parentTypes()).hasSize(3);
    HandleFunctions.handle(classStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(superClassName);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(classStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(classStatement.parentTypes().get(2), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(classStatement.declaredMethods()).isEmpty();

    assertThat(classStatement.annotations()).hasSize(1);
    HandleFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.javastatements.support.TesteeType"
    );

    assertThat(classStatement.extendedClass().orElseThrow().actualDeclaration()).isEqualTo("SuperClass");
    assertThat(classStatement.implementedInterfaces().get(0).actualDeclaration()).isEqualTo("Interface1");
    assertThat(classStatement.implementedInterfaces().get(1).actualDeclaration()).isEqualTo("Interface2");
  }

  @Test
  public void testClassWithDefaultConstructor() {
    // Given
    String canonicalClassName = "tech.intellispaces.javastatements.samples.ClassWithDefaultConstructor";
    TypeElement typeElement = getTestElement("classes/ClassWithDefaultConstructor.java");
    Session session = Sessions.get();

    // When
    ClassType classStatement = JavaStatements.classStatement(typeElement, session);
    List<MethodStatement> constructors = classStatement.constructors();

    // Then
    assertThat(constructors).hasSize(1);
    validateDefaultConstructor(constructors.get(0));
  }

  @Test
  public void testClassWithSimpleMethod() {
    testClassWithOneMethod(
        "ClassWithSimpleMethod",
        "simpleMethod",
        this::validateSimpleMethod,
        List.of());
  }

  @Test
  public void testClassWithMethodThrowsTwoExceptions() {
    testClassWithOneMethod(
        "ClassWithMethodThrowsTwoExceptions",
        "methodThrowsTwoExceptions",
        this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName()));
  }

  @Test
  public void testClassWithStaticMethod() {
    testClassWithOneMethod(
        "ClassWithStaticMethod",
        "staticMethod",
        this::validateStaticMethod,
        List.of());
  }

  @Test
  public void testClassWithMethodUsingLocalTypeParameter() {
    testClassWithOneMethod(
        "ClassWithMethodUsingLocalTypeParameter",
        "methodUsingLocalTypeParameter",
        this::validateMethodUsingLocalTypeParameter,
        List.of(List.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcard() {
    testClassWithOneMethod(
        "ClassWithMethodUsingWildcard",
        "methodUsingWildcard",
        this::validateMethodUsingWildcard,
        List.of(List.class.getCanonicalName(), Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcardThatExtendsOtherClass() {
    testClassWithOneMethod(
        "ClassWithMethodUsingWildcardThatExtendsOtherClass",
        "methodUsingWildcardThatExtendsOtherClass",
        this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithMethodUsingWildcardThatSuperOtherClass() {
    testClassWithOneMethod(
        "ClassWithMethodUsingWildcardThatSuperOtherClass",
        "methodUsingWildcardThatSuperOtherClass",
        this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testClassWithByteGetter() {
    testClassWithOneMethod(
        "ClassWithByteGetter",
        "byteGetter",
        this::validateByteGetter,
        List.of());
  }

  @Test
  public void testClassWithShortGetter() {
    testClassWithOneMethod(
        "ClassWithShortGetter",
        "shortGetter",
        this::validateShortGetter,
        List.of());
  }

  @Test
  public void testClassWithIntGetter() {
    testClassWithOneMethod(
        "ClassWithIntGetter",
        "intGetter",
        this::validateIntGetter,
        List.of());
  }

  @Test
  public void testClassWithLongGetter() {
    testClassWithOneMethod(
        "ClassWithLongGetter",
        "longGetter",
        this::validateLongGetter,
        List.of());
  }

  @Test
  public void testClassWithFloatGetter() {
    testClassWithOneMethod(
        "ClassWithFloatGetter",
        "floatGetter",
        this::validateFloatGetter,
        List.of());
  }

  @Test
  public void testClassWithDoubleGetter() {
    testClassWithOneMethod(
        "ClassWithDoubleGetter",
        "doubleGetter",
        this::validateDoubleGetter,
        List.of());
  }

  @Test
  public void testClassWithCharGetter() {
    testClassWithOneMethod(
        "ClassWithCharGetter",
        "charGetter",
        this::validateCharGetter,
        List.of());
  }

  @Test
  public void testClassWithBooleanGetter() {
    testClassWithOneMethod(
        "ClassWithBooleanGetter",
        "booleanGetter",
        this::validateBooleanGetter,
        List.of());
  }

  @Test
  public void testClassWithStringGetter() {
    testClassWithOneMethod(
        "ClassWithStringGetter",
        "stringGetter",
        this::validateStringGetter,
        List.of());
  }

  @Test
  public void testClassWithArrayOfIntGetter() {
    testClassWithOneMethod(
        "ClassWithArrayOfIntGetter",
        "arrayOfIntGetter",
        this::validateArrayOfIntGetter,
        List.of());
  }

  @Test
  public void testClassWithDoubleArrayOfStringGetter() {
    testClassWithOneMethod(
        "ClassWithDoubleArrayOfStringGetter",
        "doubleArrayOfStringGetter",
        this::validateDoubleArrayOfStringGetter,
        List.of());
  }

  @Test
  public void testClassWithEnumGetter() {
    testClassWithOneMethod(
        "ClassWithEnumGetter",
        "enumGetter",
        this::validateEnumGetter,
        List.of());
  }

  @Test
  public void testClassWithRecordGetter() {
    testClassWithOneMethod(
        "ClassWithRecordGetter",
        "recordGetter",
        this::validateRecordGetter,
        List.of());
  }

  @Test
  public void testClassWithInheritedMethodFromExtendedClass() {
    // Given
    testCustomTypeWithInheritedMethod("classes/ClassWithInheritedMethodFromExtendedClass.java");
  }

  @Test
  public void testClassWithImplementedMethodFromInterface() {
    testCustomTypeWithImplementedMethodFromInterface(
        "classes/ClassWithImplementedMethodFromInterface.java",
        List.of(),
        List.of());
  }

  @Test
  public void testClassWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface(
        "classes/ClassWithInheritedDefaultMethodFromInterface.java",
        List.of(),
        List.of());
  }

  @Test
  public void testClassWithImplementedMethod() {
    testCustomerTypeWithOverrideMethod(
        "classes/ClassWithImplementedMethod.java",
        List.of(),
        List.of());
  }

  @Test
  public void testClassWithOverrideMethod() {
    testCustomerTypeWithOverrideMethod(
        "classes/ClassWithOverrideMethod.java",
        List.of(),
        List.of());
  }

  @Test
  public void testClassWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType(
        "classes/ClassWithOverrideMethodAndNarrowedReturnType.java",
        List.of(),
        List.of());
  }

  @Test
  public void testGenericClassWithOneTypeParameter() {
    // Given
    TypeElement typeElement = getTestElement("classes/GenericClassWithOneTypeParameter.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("GenericClassWithOneTypeParameter");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("GenericClassWithOneTypeParameter<T>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("GenericClassWithOneTypeParameter<T>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration()).isEqualTo("<T>");
    assertThat(typeReference.targetType().typeParametersBriefDeclaration()).isEqualTo("<T>");

    Assertions.assertThat(typeReference.targetType().asClass()).isPresent();
    ClassType classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(1);
    HandleFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).isEmpty();
    });

    assertThat(classStatement.declaredMethods()).hasSize(1);
    HandleFunctions.handle(classStatement.declaredMethods().get(0), method -> {
      assertThat(method.isAbstract()).isFalse();
      assertThat(method.name()).isEqualTo("process");
      assertThat(method.params()).hasSize(1);
      assertThat(method.params().get(0).name()).isEqualTo("arg");
      assertThat(method.params().get(0).type().asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
      assertThat(method.returnType().orElseThrow().asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
    });
  }

  @Test
  public void testGenericClassWithMultipleTypeParameters() {
    // Given
    TypeElement typeElement = getTestElement("classes/GenericClassWithMultipleTypeParameters.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters<T1, T2 extends T1, T3 extends Number, T4 extends AutoCloseable & DataInput>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("GenericClassWithMultipleTypeParameters<T1, T2, T3, T4>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration()).isEqualTo("<T1, T2 extends T1, T3 extends Number, T4 extends AutoCloseable & DataInput>");
    assertThat(typeReference.targetType().typeParametersBriefDeclaration()).isEqualTo("<T1, T2, T3, T4>");

    Assertions.assertThat(typeReference.targetType().asClass()).isPresent();
    ClassType classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(4);
    HandleFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T1");
      assertThat(typeParam.extendedBounds()).isEmpty();
      assertThat(typeParam.actualDeclaration()).isEqualTo("T1");
      assertThat(typeParam.formalFullDeclaration()).isEqualTo("T1");
      assertThat(typeParam.formalBriefDeclaration()).isEqualTo("T1");
    });
    HandleFunctions.handle(classStatement.typeParameters().get(1), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T2");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));
      assertThat(typeParam.actualDeclaration()).isEqualTo("T2");
      assertThat(typeParam.formalFullDeclaration()).isEqualTo("T2 extends T1");
      assertThat(typeParam.formalBriefDeclaration()).isEqualTo("T2");
    });
    HandleFunctions.handle(classStatement.typeParameters().get(2), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T3");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Number.class.getCanonicalName());
      assertThat(typeParam.actualDeclaration()).isEqualTo("T3");
      assertThat(typeParam.formalFullDeclaration()).isEqualTo("T3 extends Number");
      assertThat(typeParam.formalBriefDeclaration()).isEqualTo("T3");
    });

    HandleFunctions.handle(classStatement.typeParameters().get(3), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T4");
      assertThat(typeParam.extendedBounds()).hasSize(2);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(AutoCloseable.class.getCanonicalName());
      assertThat(typeParam.extendedBounds().get(1).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(DataInput.class.getCanonicalName());
      assertThat(typeParam.actualDeclaration()).isEqualTo("T4");
      assertThat(typeParam.formalFullDeclaration()).isEqualTo("T4 extends AutoCloseable & DataInput");
      assertThat(typeParam.formalBriefDeclaration()).isEqualTo("T4");
    });

    assertThat(classStatement.declaredMethods()).hasSize(1);
    HandleFunctions.handle(classStatement.declaredMethods().get(0), method -> {
      assertThat(method.name()).isEqualTo("process");
      assertThat(method.params()).hasSize(2);

      assertThat(method.params().get(0).name()).isEqualTo("arg1");
      assertThat(method.params().get(0).type().asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(0));

      assertThat(method.params().get(1).name()).isEqualTo("arg2");
      assertThat(method.params().get(1).type().asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(2));

      assertThat(method.returnType().orElseThrow().asNamedReference().orElseThrow()).isSameAs(classStatement.typeParameters().get(1));
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.javastatements.support.TesteeType",
        "java.io.DataInput"
    );
  }

  @Test
  public void testGenericClassWithCyclicTypeDependencyCase1() {
    // Given
    TypeElement typeElement = getTestElement("classes/GenericClassWithCyclicTypeDependencyCase1.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("GenericClassWithCyclicTypeDependencyCase1<T>");

    Assertions.assertThat(typeReference.targetType().asClass()).isPresent();
    ClassType classStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classStatement.typeParameters()).hasSize(1);
    HandleFunctions.handle(classStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(classStatement);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments().get(0)
          .asNamedReference().orElseThrow().name()).isEqualTo("T");
    });
  }

  @Test
  public void testGenericClassWithCyclicTypeDependencyCase2() {
    // Given
    TypeElement typeElement = getTestElement("classes/GenericClassWithCyclicTypeDependencyCase2.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("ClassA");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("ClassA<T1 extends ClassB<?>>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("ClassA<T1>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration()).isEqualTo("<T1 extends ClassB<?>>");
    assertThat(typeReference.targetType().typeParametersBriefDeclaration()).isEqualTo("<T1>");

    Assertions.assertThat(typeReference.targetType().asClass()).isPresent();
    ClassType classAStatement = typeReference.targetType().asClass().orElseThrow();

    assertThat(classAStatement.typeParameters()).hasSize(1);
    HandleFunctions.handle(classAStatement.typeParameters().get(0), classATypeParam -> {
      assertThat(classATypeParam.name()).isEqualTo("T1");
      assertThat(classATypeParam.extendedBounds()).hasSize(1);
      HandleFunctions.handle(classATypeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType(), classBExtendedBound -> {
        assertThat(classBExtendedBound.canonicalName()).isEqualTo("tech.intellispaces.javastatements.samples.GenericClassWithCyclicTypeDependencyCase2.ClassB");
        assertThat(classBExtendedBound.className()).isEqualTo("tech.intellispaces.javastatements.samples.GenericClassWithCyclicTypeDependencyCase2$ClassB");
        assertThat(classBExtendedBound.typeParameters()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().name()).isEqualTo("T2");
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().extendedBounds()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().extendedBounds().get(0)
            .asCustomTypeReference().orElseThrow().targetType()).isSameAs(classAStatement);
      });
    });
  }

  private void testClassWithOneMethod(
      String className, String methodName, Consumer<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String canonicalClassName = "tech.intellispaces.javastatements.samples." + className;
    TypeElement typeElement = getTestElement("classes/" + className + ".java");
    Session session = Sessions.get();

    // When
    ClassType classStatement = JavaStatements.classStatement(typeElement, session);

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
    methodValidator.accept(declaredMethods.get(0));

    List<MethodStatement> actualMethods = classStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.accept(actualMethods.get(0));

    assertThat(classStatement.annotations()).hasSize(1);
    HandleFunctions.handle(classStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(classStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports, "tech.intellispaces.javastatements.support.TesteeType")
    );

    assertThat(session.getType(canonicalClassName)).isSameAs(classStatement);
  }
}
