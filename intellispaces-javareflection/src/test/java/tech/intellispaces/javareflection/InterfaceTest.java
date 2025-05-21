package tech.intellispaces.javareflection;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import tech.intellispaces.commons.collection.CollectionFunctions;
import tech.intellispaces.commons.object.ObjectFunctions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.javareflection.customtype.CustomType;
import tech.intellispaces.javareflection.customtype.InterfaceType;
import tech.intellispaces.javareflection.method.MethodStatement;
import tech.intellispaces.javareflection.reference.CustomTypeReference;
import tech.intellispaces.javareflection.session.Session;
import tech.intellispaces.javareflection.session.Sessions;
import tech.intellispaces.javareflection.support.TesteeType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InterfaceType}.
 */
public class InterfaceTest extends AbstractCustomStatementTest {

  @Test
  public void testEmptyInterface() {
    // Given
    TypeElement typeElement = getTestElement("interfaces/EmptyInterface.java");

    // When
    CustomType customStatementType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customStatementType).isInstanceOf(InterfaceType.class);
    InterfaceType interfaceStatement = customStatementType.asInterface().orElse(null);
    assertThat(interfaceStatement).isNotNull();

    assertThat(interfaceStatement.isNested()).isFalse();
    assertThat(interfaceStatement.isAbstract()).isTrue();
    assertThat(interfaceStatement.simpleName()).isEqualTo("EmptyInterface");
    assertThat(interfaceStatement.canonicalName()).isEqualTo("tech.intellispaces.javareflection.samples.EmptyInterface");
    assertThat(interfaceStatement.className()).isEqualTo("tech.intellispaces.javareflection.samples.EmptyInterface");

    assertThat(interfaceStatement.typeParameters()).isEmpty();
    assertThat(interfaceStatement.extendedInterfaces()).isEmpty();
    assertThat(interfaceStatement.parentTypes()).isEmpty();
    assertThat(interfaceStatement.declaredMethods()).isEmpty();

    assertThat(interfaceStatement.annotations()).hasSize(1);
    ObjectFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.javareflection.support.TesteeType"
    );
  }

  @Test
  public void testNestedInterface() {
    // Given
    TypeElement typeElement = getTestElement("interfaces/NestedInterface.java");

    // When
    InterfaceType interfaceStatement = JavaStatements.interfaceStatement(typeElement);

    // Then
    assertThat(interfaceStatement.isNested()).isTrue();
    assertThat(interfaceStatement.simpleName()).isEqualTo("NestedInterface2");
    assertThat(interfaceStatement.canonicalName()).isEqualTo("tech.intellispaces.javareflection.samples.NestedInterface.NestedInterface1.NestedInterface2");
    assertThat(interfaceStatement.className()).isEqualTo("tech.intellispaces.javareflection.samples.NestedInterface$NestedInterface1$NestedInterface2");
  }

  @Test
  public void testInterfaceExtendedTwoInterfaces() {
    // Given
    final var interface1Name = "tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface1";
    final var interface2Name = "tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("interfaces/InterfaceExtendedTwoInterfaces.java");

    // When
    InterfaceType interfaceStatement = JavaStatements.interfaceStatement(typeElement);

    // Then
    assertThat(interfaceStatement).isNotNull();
    assertThat(interfaceStatement.simpleName()).isEqualTo("TesteeInterface");
    assertThat(interfaceStatement.canonicalName()).isEqualTo("tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.TesteeInterface");
    assertThat(interfaceStatement.className()).isEqualTo("tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces$TesteeInterface");

    assertThat(interfaceStatement.hasParent(interface1Name)).isTrue();
    assertThat(interfaceStatement.hasParent(interface2Name)).isTrue();
    assertThat(interfaceStatement.hasParent(Object.class.getCanonicalName())).isFalse();

    assertThat(interfaceStatement.typeParameters()).isEmpty();

    assertThat(interfaceStatement.extendedInterfaces()).hasSize(2);
    ObjectFunctions.handle(interfaceStatement.extendedInterfaces().get(0), extendedInterface -> {
      assertThat(extendedInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(extendedInterface.typeArguments()).isEmpty();
    });
    ObjectFunctions.handle(interfaceStatement.extendedInterfaces().get(1), extendedInterface -> {
      assertThat(extendedInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(extendedInterface.typeArguments()).isEmpty();
    });

    assertThat(interfaceStatement.parentTypes()).hasSize(2);
    ObjectFunctions.handle(interfaceStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    ObjectFunctions.handle(interfaceStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(interfaceStatement.declaredMethods()).isEmpty();

    assertThat(interfaceStatement.annotations()).hasSize(1);
    ObjectFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.javareflection.support.TesteeType",
        "tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface1",
        "tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface2"
    );

    assertThat(interfaceStatement.extendedInterfaces().get(0).actualDeclaration()).isEqualTo("tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface1");
    assertThat(interfaceStatement.extendedInterfaces().get(1).actualDeclaration()).isEqualTo("tech.intellispaces.javareflection.samples.InterfaceExtendedTwoInterfaces.Interface2");
  }

  @Test
  public void testInterfaceWithSimpleMethod() {
    testInterfaceWithOneMethod("InterfaceWithSimpleMethod", "simpleMethod", this::validateSimpleMethod, List.of());
  }

  @Test
  public void testInterfaceWithDefaultMethod() {
    testInterfaceWithOneMethod("InterfaceWithDefaultMethod", "defaultMethod", this::validateDefaultMethod, List.of());
  }

  @Test
  public void testInterfaceWithMethodThrowsTwoExceptions() {
    testInterfaceWithOneMethod(
            "InterfaceWithMethodThrowsTwoExceptions",
            "methodThrowsTwoExceptions",
            this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName(), ClassNotFoundException.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithStaticMethod() {
    testInterfaceWithOneMethod("InterfaceWithStaticMethod", "staticMethod", this::validateStaticMethod, List.of());
  }

  @Test
  public void testInterfaceWithMethodUsingLocalTypeParameter() {
    testInterfaceWithOneMethod("InterfaceWithMethodUsingLocalTypeParameter", "methodUsingLocalTypeParameter", this::validateMethodUsingLocalTypeParameter,
        List.of(List.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithMethodUsingWildcard() {
    testInterfaceWithOneMethod("InterfaceWithMethodUsingWildcard", "methodUsingWildcard", this::validateMethodUsingWildcard,
        List.of(List.class.getCanonicalName(), Collection.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithMethodUsingWildcardThatExtendsOtherClass() {
    testInterfaceWithOneMethod(
            "InterfaceWithMethodUsingWildcardThatExtendsOtherClass",
            "methodUsingWildcardThatExtendsOtherClass",
            this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName(), Number.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithMethodUsingWildcardThatSuperOtherClass() {
    testInterfaceWithOneMethod(
            "InterfaceWithMethodUsingWildcardThatSuperOtherClass",
            "methodUsingWildcardThatSuperOtherClass",
            this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName(), Number.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithByteGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithByteGetter",
        "byteGetter",
        this::validateAbstractByteGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithShortGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithShortGetter",
        "shortGetter",
        this::validateAbstractShortGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithIntGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithIntGetter",
        "intGetter",
        this::validateAbstractIntGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithLongGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithLongGetter",
        "longGetter",
        this::validateAbstractLongGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithFloatGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithFloatGetter",
        "floatGetter",
        this::validateAbstractFloatGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithDoubleGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithDoubleGetter",
        "doubleGetter",
        this::validateAbstractDoubleGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithCharGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithCharGetter",
        "charGetter",
        this::validateAbstractCharGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithBooleanGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithBooleanGetter",
        "booleanGetter",
        this::validateAbstractBooleanGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithStringGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithStringGetter",
        "stringGetter",
        this::validateAbstractStringGetter,
        List.of(String.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithArrayOfIntGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithArrayOfIntGetter",
        "arrayOfIntGetter",
        this::validateAbstractArrayOfIntGetter,
        List.of());
  }

  @Test
  public void testInterfaceWithDoubleArrayOfStringGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithDoubleArrayOfStringGetter",
        "doubleArrayOfStringGetter",
        this::validateAbstractDoubleArrayOfStringGetter,
        List.of(String.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithEnumGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithEnumGetter",
        "enumGetter",
        this::validateAbstractEnumGetter,
        List.of("tech.intellispaces.javareflection.samples.TestEnum"));
  }

  @Test
  public void testInterfaceWithRecordGetter() {
    testInterfaceWithOneMethod(
        "InterfaceWithRecordGetter",
        "recordGetter",
        this::validateAbstractRecordGetter,
        List.of("tech.intellispaces.javareflection.samples.TestRecord"));
  }

  @Test
  public void testInterfaceWithInheritedMethodFromExtendedClass() {
    // Given
    testCustomTypeWithInheritedMethod("interfaces/InterfaceWithInheritedMethod.java");
  }

  @Test
  public void testInterfaceWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface(
        "interfaces/InterfaceWithInheritedDefaultMethodFromInterface.java",
        List.of(),
        List.of());
  }

  @Test
  public void testInterfaceWithOverrideMethod() {
    testCustomerTypeWithOverrideMethod(
        "interfaces/InterfaceWithOverrideMethod.java",
        List.of(),
        List.of());
  }

  @Test
  public void testInterfaceWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType(
        "interfaces/InterfaceWithOverrideMethodAndNarrowedReturnType.java",
        List.of(),
        List.of());
  }

  @Test
  public void testGenericInterfaceWithCyclicTypeDependencyCase1() {
    // Given
    TypeElement typeElement = getTestElement("interfaces/GenericInterfaceWithCyclicTypeDependencyCase1.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase1");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("GenericInterfaceWithCyclicTypeDependencyCase1<T extends tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("GenericInterfaceWithCyclicTypeDependencyCase1<T>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration()).isEqualTo("<T extends tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration(ClassNameFunctions::getSimpleName)).isEqualTo("<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.targetType().typeParametersBriefDeclaration()).isEqualTo("<T>");

    Assertions.assertThat(typeReference.targetType().asInterface()).isPresent();
    InterfaceType interfaceStatement = typeReference.targetType().asInterface().orElseThrow();

    assertThat(interfaceStatement.typeParameters()).hasSize(1);
    ObjectFunctions.handle(interfaceStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(interfaceStatement);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments().get(0)
          .asNamedReference().orElseThrow().name()).isEqualTo("T");
    });
  }

  @Test
  public void testGenericInterfaceWithCyclicTypeDependencyCase2() {
    // Given
    TypeElement typeElement = getTestElement("interfaces/GenericInterfaceWithCyclicTypeDependencyCase2.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.actualDeclaration()).isEqualTo("tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase2.InterfaceA");
    assertThat(typeReference.formalFullDeclaration()).isEqualTo("InterfaceA<T1 extends tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase2.InterfaceB<?>>");
    assertThat(typeReference.formalBriefDeclaration()).isEqualTo("InterfaceA<T1>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration()).isEqualTo("<T1 extends tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase2.InterfaceB<?>>");
    assertThat(typeReference.targetType().typeParametersFullDeclaration(ClassNameFunctions::getSimpleName)).isEqualTo("<T1 extends InterfaceB<?>>");
    assertThat(typeReference.targetType().typeParametersBriefDeclaration()).isEqualTo("<T1>");

    Assertions.assertThat(typeReference.targetType().asInterface()).isPresent();
    InterfaceType interfaceStatement = typeReference.targetType().asInterface().orElseThrow();

    assertThat(interfaceStatement.typeParameters()).hasSize(1);
    ObjectFunctions.handle(interfaceStatement.typeParameters().get(0), classATypeParam -> {
      assertThat(classATypeParam.name()).isEqualTo("T1");
      assertThat(classATypeParam.extendedBounds()).hasSize(1);
      ObjectFunctions.handle(classATypeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType(), classBExtendedBound -> {
        assertThat(classBExtendedBound.canonicalName()).isEqualTo("tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase2.InterfaceB");
        assertThat(classBExtendedBound.className()).isEqualTo("tech.intellispaces.javareflection.samples.GenericInterfaceWithCyclicTypeDependencyCase2$InterfaceB");
        assertThat(classBExtendedBound.typeParameters()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().name()).isEqualTo("T2");
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().extendedBounds()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedReference().orElseThrow().extendedBounds().get(0)
            .asCustomTypeReference().orElseThrow().targetType()).isSameAs(interfaceStatement);
      });
    });
  }

  private void testInterfaceWithOneMethod(
      String interfaceName, String methodName, Consumer<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String canonicalClassName = "tech.intellispaces.javareflection.samples." + interfaceName;
    TypeElement typeElement = getTestElement("interfaces/" + interfaceName + ".java");
    Session session = Sessions.get();

    // When
    InterfaceType interfaceStatement = JavaStatements.interfaceStatement(typeElement, session);

    // Then
    assertThat(interfaceStatement.simpleName()).isEqualTo(interfaceName);
    assertThat(interfaceStatement.canonicalName()).isEqualTo(canonicalClassName);

    assertThat(interfaceStatement.typeParameters()).isEmpty();
    assertThat(interfaceStatement.extendedInterfaces()).isEmpty();
    assertThat(interfaceStatement.parentTypes()).isEmpty();

    assertThat(interfaceStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly(methodName);

    assertThat(interfaceStatement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly(methodName);

    List<MethodStatement> declaredMethods = interfaceStatement.declaredMethodsWithName(methodName);
    assertThat(declaredMethods).hasSize(1);
    methodValidator.accept(declaredMethods.get(0));

    List<MethodStatement> actualMethods = interfaceStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.accept(actualMethods.get(0));

    assertThat(interfaceStatement.annotations()).hasSize(1);
    ObjectFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports,"tech.intellispaces.javareflection.support.TesteeType")
    );

    assertThat(session.getType(canonicalClassName)).isSameAs(interfaceStatement);
  }

  private void validateDefaultMethod(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isTrue();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }
}
