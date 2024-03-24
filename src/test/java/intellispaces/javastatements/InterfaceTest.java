package intellispaces.javastatements;

import intellispaces.commons.function.CommonFunctions;
import intellispaces.commons.function.CollectionFunctions;
import intellispaces.commons.model.action.HandlerAction;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.InterfaceStatement;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.session.SessionFactory;
import intellispaces.javastatements.support.TesteeType;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link intellispaces.javastatements.model.custom.InterfaceStatement}.
 */
public class InterfaceTest extends AbstractCustomTypeTest {

  @Test
  public void testEmptyInterface() {
    // Given
    TypeElement typeElement = getTestElement("interface/EmptyInterface.java");

    // When
    CustomType customTypeStatement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customTypeStatement).isInstanceOf(InterfaceStatement.class);
    InterfaceStatement interfaceStatement = customTypeStatement.asInterface().orElse(null);
    assertThat(interfaceStatement).isNotNull();

    assertThat(interfaceStatement.simpleName()).isEqualTo("EmptyInterface");
    assertThat(interfaceStatement.canonicalName()).isEqualTo("intellispaces.javastatements.sample.EmptyInterface");

    assertThat(interfaceStatement.typeParameters()).isEmpty();
    assertThat(interfaceStatement.extendedInterfaces()).isEmpty();
    assertThat(interfaceStatement.parentTypes()).isEmpty();
    assertThat(interfaceStatement.declaredMethods()).isEmpty();

    assertThat(interfaceStatement.annotations()).hasSize(1);
    CommonFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "intellispaces.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testInterfaceExtendedTwoInterfaces() {
    // Given
    final var interface1Name = "intellispaces.javastatements.sample.InterfaceExtendedTwoInterfaces.Interface1";
    final var interface2Name = "intellispaces.javastatements.sample.InterfaceExtendedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("interface/InterfaceExtendedTwoInterfaces.java");

    // When
    InterfaceStatement interfaceStatement = JavaStatements.interfaceStatement(typeElement);

    // Then
    assertThat(interfaceStatement).isNotNull();
    assertThat(interfaceStatement.simpleName()).isEqualTo("TesteeInterface");
    assertThat(interfaceStatement.canonicalName()).isEqualTo("intellispaces.javastatements.sample.InterfaceExtendedTwoInterfaces.TesteeInterface");

    assertThat(interfaceStatement.typeParameters()).isEmpty();

    assertThat(interfaceStatement.extendedInterfaces()).hasSize(2);
    CommonFunctions.handle(interfaceStatement.extendedInterfaces().get(0), extendedInterface -> {
      assertThat(extendedInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(extendedInterface.typeArguments()).isEmpty();
    });
    CommonFunctions.handle(interfaceStatement.extendedInterfaces().get(1), extendedInterface -> {
      assertThat(extendedInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(extendedInterface.typeArguments()).isEmpty();
    });

    assertThat(interfaceStatement.parentTypes()).hasSize(2);
    CommonFunctions.handle(interfaceStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    CommonFunctions.handle(interfaceStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(interfaceStatement.declaredMethods()).isEmpty();

    assertThat(interfaceStatement.annotations()).hasSize(1);
    CommonFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "intellispaces.javastatements.support.TesteeType"
    );

    assertThat(interfaceStatement.extendedInterfaces().get(0).referenceDeclaration()).isEqualTo("Interface1");
    assertThat(interfaceStatement.extendedInterfaces().get(1).referenceDeclaration()).isEqualTo("Interface2");
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
    testInterfaceWithOneMethod("InterfaceWithMethodThrowsTwoExceptions", "methodThrowsTwoExceptions", this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName()));
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
    testInterfaceWithOneMethod("InterfaceWithMethodUsingWildcardThatExtendsOtherClass", "methodUsingWildcardThatExtendsOtherClass", this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithMethodUsingWildcardThatSuperOtherClass() {
    testInterfaceWithOneMethod("InterfaceWithMethodUsingWildcardThatSuperOtherClass", "methodUsingWildcardThatSuperOtherClass", this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testInterfaceWithByteGetter() {
    testInterfaceWithOneMethod("InterfaceWithByteGetter", "byteGetter", this::validateByteGetter, List.of());
  }

  @Test
  public void testInterfaceWithShortGetter() {
    testInterfaceWithOneMethod("InterfaceWithShortGetter", "shortGetter", this::validateShortGetter, List.of());
  }

  @Test
  public void testInterfaceWithIntGetter() {
    testInterfaceWithOneMethod("InterfaceWithIntGetter", "intGetter", this::validateIntGetter, List.of());
  }

  @Test
  public void testInterfaceWithLongGetter() {
    testInterfaceWithOneMethod("InterfaceWithLongGetter", "longGetter", this::validateLongGetter, List.of());
  }

  @Test
  public void testInterfaceWithFloatGetter() {
    testInterfaceWithOneMethod("InterfaceWithFloatGetter", "floatGetter", this::validateFloatGetter, List.of());
  }

  @Test
  public void testInterfaceWithDoubleGetter() {
    testInterfaceWithOneMethod("InterfaceWithDoubleGetter", "doubleGetter", this::validateDoubleGetter, List.of());
  }

  @Test
  public void testInterfaceWithCharGetter() {
    testInterfaceWithOneMethod("InterfaceWithCharGetter", "charGetter", this::validateCharGetter, List.of());
  }

  @Test
  public void testInterfaceWithBooleanGetter() {
    testInterfaceWithOneMethod("InterfaceWithBooleanGetter", "booleanGetter", this::validateBooleanGetter, List.of());
  }

  @Test
  public void testInterfaceWithStringGetter() {
    testInterfaceWithOneMethod("InterfaceWithStringGetter", "stringGetter", this::validateStringGetter, List.of());
  }

  @Test
  public void testInterfaceWithArrayOfIntGetter() {
    testInterfaceWithOneMethod("InterfaceWithArrayOfIntGetter", "arrayOfIntGetter", this::validateArrayOfIntGetter, List.of());
  }

  @Test
  public void testInterfaceWithDoubleArrayOfStringGetter() {
    testInterfaceWithOneMethod("InterfaceWithDoubleArrayOfStringGetter", "doubleArrayOfStringGetter", this::validateDoubleArrayOfStringGetter, List.of());
  }

  @Test
  public void testInterfaceWithEnumGetter() {
    testInterfaceWithOneMethod("InterfaceWithEnumGetter", "enumGetter", this::validateEnumGetter, List.of());
  }

  @Test
  public void testInterfaceWithRecordGetter() {
    testInterfaceWithOneMethod("InterfaceWithRecordGetter", "recordGetter", this::validateRecordGetter, List.of());
  }

  @Test
  public void testInterfaceWithInheritedMethodFromExtendedClass() {
    // Given
    testCustomTypeWithInheritedMethod("interface/InterfaceWithInheritedMethod.java");
  }

  @Test
  public void testInterfaceWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface("interface/InterfaceWithInheritedDefaultMethodFromInterface.java", List.of(), List.of());
  }

  @Test
  public void testInterfaceWithOverrideMethod() {
    testCustomerTypeWithOverrideMethod("interface/InterfaceWithOverrideMethod.java", List.of(), List.of());
  }

  @Test
  public void testInterfaceWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType("interface/InterfaceWithOverrideMethodAndNarrowedReturnType.java", List.of(), List.of());
  }

  @Test
  public void testGenericInterfaceWithCyclicTypeDependencyCase1() {
    // Given
    TypeElement typeElement = getTestElement("interface/GenericInterfaceWithCyclicTypeDependencyCase1.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("GenericInterfaceWithCyclicTypeDependencyCase1");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("GenericInterfaceWithCyclicTypeDependencyCase1<T>");

    assertThat(typeReference.targetType().asInterface()).isPresent();
    InterfaceStatement interfaceStatement = typeReference.targetType().asInterface().orElseThrow();

    assertThat(interfaceStatement.typeParameters()).hasSize(1);
    CommonFunctions.handle(interfaceStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(interfaceStatement);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments().get(0)
          .asNamedTypeReference().orElseThrow().name()).isEqualTo("T");
    });
  }

  @Test
  public void testGenericInterfaceWithCyclicTypeDependencyCase2() {
    // Given
    TypeElement typeElement = getTestElement("interface/GenericInterfaceWithCyclicTypeDependencyCase2.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("InterfaceA");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("InterfaceA<T1 extends InterfaceB<?>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("InterfaceA<T1>");

    assertThat(typeReference.targetType().asInterface()).isPresent();
    InterfaceStatement interfaceStatement = typeReference.targetType().asInterface().orElseThrow();

    assertThat(interfaceStatement.typeParameters()).hasSize(1);
    CommonFunctions.handle(interfaceStatement.typeParameters().get(0), classATypeParam -> {
      assertThat(classATypeParam.name()).isEqualTo("T1");
      assertThat(classATypeParam.extendedBounds()).hasSize(1);
      CommonFunctions.handle(classATypeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType(), classBExtendedBound -> {
        assertThat(classBExtendedBound.canonicalName()).isEqualTo("intellispaces.javastatements.sample.GenericInterfaceWithCyclicTypeDependencyCase2.InterfaceB");
        assertThat(classBExtendedBound.typeParameters()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().name()).isEqualTo("T2");
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds().get(0)
            .asCustomTypeReference().orElseThrow().targetType()).isSameAs(interfaceStatement);
      });
    });
  }

  private void testInterfaceWithOneMethod(
      String interfaceName, String methodName, HandlerAction<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String canonicalClassName = "intellispaces.javastatements.sample." + interfaceName;
    TypeElement typeElement = getTestElement("interface/" + interfaceName + ".java");
    Session session = SessionFactory.create();

    // When
    InterfaceStatement interfaceStatement = JavaStatements.interfaceStatement(typeElement, session);

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
    methodValidator.execute(declaredMethods.get(0));

    List<MethodStatement> actualMethods = interfaceStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.execute(actualMethods.get(0));

    assertThat(interfaceStatement.annotations()).hasSize(1);
    CommonFunctions.handle(interfaceStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(interfaceStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports, "intellispaces.javastatements.support.TesteeType")
    );

    assertThat(session.getType(canonicalClassName)).isSameAs(interfaceStatement);
  }

  private void validateDefaultMethod(MethodStatement method) {
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isTrue();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }
}
