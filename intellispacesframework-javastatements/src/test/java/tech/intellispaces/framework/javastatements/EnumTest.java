package tech.intellispaces.framework.javastatements;

import org.junit.jupiter.api.Test;
import tech.intellispaces.framework.commons.collection.CollectionFunctions;
import tech.intellispaces.framework.commons.datahandle.HandleFunctions;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.session.SessionBuilder;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.support.TesteeType;

import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EnumStatement}.
 */
public class EnumTest extends AbstractCustomTypeTest {

  @Test
  public void testEmptyEnum() {
    // Given
    TypeElement typeElement = getTestElement("enums/EmptyEnum.java");

    // When
    CustomType customTypeStatement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customTypeStatement).isInstanceOf(EnumStatement.class);
    EnumStatement enumStatement = customTypeStatement.asEnum().orElse(null);
    assertThat(enumStatement).isNotNull();

    assertThat(enumStatement.isAbstract()).isFalse();
    assertThat(enumStatement.simpleName()).isEqualTo("EmptyEnum");
    assertThat(enumStatement.canonicalName()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EmptyEnum");
    assertThat(enumStatement.className()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EmptyEnum");

    assertThat(enumStatement.typeParameters()).isEmpty();
    assertThat(enumStatement.implementedInterfaces()).isEmpty();

    assertThat(enumStatement.parentTypes()).hasSize(1);
    assertThat(enumStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Enum.class.getCanonicalName());

    assertThat(enumStatement.declaredMethods()).hasSize(2);
    assertThat(enumStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder("values", "valueOf");

    assertThat(enumStatement.annotations()).hasSize(1);
    HandleFunctions.handle(enumStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(enumStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.framework.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testEnumImplementedTwoInterfaces() {
    // Given
    final var interface1Name = "tech.intellispaces.framework.javastatements.samples.EnumImplementedTwoInterfaces.Interface1";
    final var interface2Name = "tech.intellispaces.framework.javastatements.samples.EnumImplementedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("enums/EnumImplementedTwoInterfaces.java");

    // When
    EnumStatement enumStatement = JavaStatements.enumStatement(typeElement);

    // Then
    assertThat(enumStatement).isNotNull();
    assertThat(enumStatement.simpleName()).isEqualTo("TesteeEnum");
    assertThat(enumStatement.canonicalName()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EnumImplementedTwoInterfaces.TesteeEnum");
    assertThat(enumStatement.className()).isEqualTo("tech.intellispaces.framework.javastatements.samples.EnumImplementedTwoInterfaces$TesteeEnum");

    assertThat(enumStatement.hasParent(interface1Name)).isTrue();
    assertThat(enumStatement.hasParent(interface2Name)).isTrue();
    assertThat(enumStatement.hasParent(Object.class.getCanonicalName())).isFalse();
    assertThat(enumStatement.hasParent(Enum.class.getCanonicalName())).isTrue();

    assertThat(enumStatement.typeParameters()).isEmpty();

    assertThat(enumStatement.implementedInterfaces()).hasSize(2);
    HandleFunctions.handle(enumStatement.implementedInterfaces().get(0), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(enumStatement.implementedInterfaces().get(1), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });

    assertThat(enumStatement.parentTypes()).hasSize(3);
    HandleFunctions.handle(enumStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(Enum.class.getCanonicalName());
      assertThat(parentType.typeArguments()).hasSize(1);
      assertThat(parentType.typeArguments().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(enumStatement);
    });
    HandleFunctions.handle(enumStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(enumStatement.parentTypes().get(2), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(enumStatement.declaredMethods()).hasSize(2);
    assertThat(enumStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder("values", "valueOf");

    assertThat(enumStatement.annotations()).hasSize(1);
    HandleFunctions.handle(enumStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(enumStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispaces.framework.javastatements.support.TesteeType"
    );

    assertThat(enumStatement.implementedInterfaces().get(0).actualDeclaration()).isEqualTo("Interface1");
    assertThat(enumStatement.implementedInterfaces().get(1).actualDeclaration()).isEqualTo("Interface2");
  }

  @Test
  public void testEnumWithSimpleMethod() {
    testEnumWithOneMethod(
        "EnumWithSimpleMethod",
        "simpleMethod",
        this::validateSimpleMethod,
        List.of());
  }

  @Test
  public void testEnumWithMethodThrowsTwoExceptions() {
    testEnumWithOneMethod(
        "EnumWithMethodThrowsTwoExceptions",
        "methodThrowsTwoExceptions",
        this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName()));
  }

  @Test
  public void testEnumWithStaticMethod() {
    testEnumWithOneMethod(
        "EnumWithStaticMethod",
        "staticMethod",
        this::validateStaticMethod,
        List.of());
  }

  @Test
  public void testEnumWithMethodUsingLocalTypeParameter() {
    testEnumWithOneMethod(
        "EnumWithMethodUsingLocalTypeParameter",
        "methodUsingLocalTypeParameter",
        this::validateMethodUsingLocalTypeParameter,
        List.of(List.class.getCanonicalName()));
  }

  @Test
  public void testEnumWithMethodUsingWildcard() {
    testEnumWithOneMethod(
        "EnumWithMethodUsingWildcard",
        "methodUsingWildcard",
        this::validateMethodUsingWildcard,
        List.of(List.class.getCanonicalName(), Collection.class.getCanonicalName()));
  }

  @Test
  public void testEnumWithMethodUsingWildcardThatExtendsOtherClass() {
    testEnumWithOneMethod(
        "EnumWithMethodUsingWildcardThatExtendsOtherClass",
        "methodUsingWildcardThatExtendsOtherClass",
        this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testEnumWithMethodUsingWildcardThatSuperOtherClass() {
    testEnumWithOneMethod(
        "EnumWithMethodUsingWildcardThatSuperOtherClass",
        "methodUsingWildcardThatSuperOtherClass",
        this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testEnumWithByteGetter() {
    testEnumWithOneMethod(
        "EnumWithByteGetter",
        "byteGetter",
        this::validateByteGetter,
        List.of());
  }

  @Test
  public void testEnumWithShortGetter() {
    testEnumWithOneMethod(
        "EnumWithShortGetter",
        "shortGetter",
        this::validateShortGetter,
        List.of());
  }

  @Test
  public void testEnumWithIntGetter() {
    testEnumWithOneMethod(
        "EnumWithIntGetter",
        "intGetter",
        this::validateIntGetter,
        List.of());
  }

  @Test
  public void testEnumWithLongGetter() {
    testEnumWithOneMethod(
        "EnumWithLongGetter",
        "longGetter",
        this::validateLongGetter,
        List.of());
  }

  @Test
  public void testEnumWithFloatGetter() {
    testEnumWithOneMethod(
        "EnumWithFloatGetter",
        "floatGetter",
        this::validateFloatGetter,
        List.of());
  }

  @Test
  public void testEnumWithDoubleGetter() {
    testEnumWithOneMethod(
        "EnumWithDoubleGetter",
        "doubleGetter",
        this::validateDoubleGetter,
        List.of());
  }

  @Test
  public void testEnumWithCharGetter() {
    testEnumWithOneMethod(
        "EnumWithCharGetter",
        "charGetter",
        this::validateCharGetter,
        List.of());
  }

  @Test
  public void testEnumWithBooleanGetter() {
    testEnumWithOneMethod(
        "EnumWithBooleanGetter",
        "booleanGetter",
        this::validateBooleanGetter,
        List.of());
  }

  @Test
  public void testEnumWithStringGetter() {
    testEnumWithOneMethod(
        "EnumWithStringGetter",
        "stringGetter",
        this::validateStringGetter,
        List.of());
  }

  @Test
  public void testEnumWithArrayOfIntGetter() {
    testEnumWithOneMethod(
        "EnumWithArrayOfIntGetter",
        "arrayOfIntGetter",
        this::validateArrayOfIntGetter,
        List.of());
  }

  @Test
  public void testEnumWithDoubleArrayOfStringGetter() {
    testEnumWithOneMethod(
        "EnumWithDoubleArrayOfStringGetter",
        "doubleArrayOfStringGetter",
        this::validateDoubleArrayOfStringGetter,
        List.of());
  }

  @Test
  public void testEnumWithEnumGetter() {
    testEnumWithOneMethod(
        "EnumWithEnumGetter",
        "enumGetter",
        this::validateEnumGetter,
        List.of());
  }

  @Test
  public void testEnumWithRecordGetter() {
    testEnumWithOneMethod(
        "EnumWithRecordGetter",
        "recordGetter",
        this::validateRecordGetter,
        List.of());
  }

  @Test
  public void testEnumWithImplementedMethodFromInterface() {
    testCustomTypeWithImplementedMethodFromInterface(
        "enums/EnumWithImplementedMethodFromInterface.java",
        List.of("valueOf", "values"),
        List.of("valueOf", "values", "readObjectNoData", "compareTo", "describeConstable", "readObject",
            "getDeclaringClass", "hashCode", "equals", "name", "clone", "toString", "finalize", "ordinal"));
  }

  @Test
  public void testEnumWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface(
        "enums/EnumWithInheritedDefaultMethodFromInterface.java",
        List.of("valueOf", "values"),
        List.of("valueOf", "values", "readObjectNoData", "compareTo", "describeConstable", "readObject",
            "getDeclaringClass", "hashCode", "equals", "name", "clone", "toString", "finalize", "ordinal"));
  }

  @Test
  public void testEnumWithImplementedMethod() {
    testCustomerTypeWithOverrideMethod(
        "enums/EnumWithImplementedMethod.java",
        List.of("valueOf", "values"),
        List.of("valueOf", "values", "readObjectNoData", "compareTo", "describeConstable", "readObject",
            "getDeclaringClass", "hashCode", "equals", "name", "clone", "toString", "finalize", "ordinal"));
  }

  @Test
  public void testEnumWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType(
        "enums/EnumWithOverrideMethodAndNarrowedReturnType.java",
        List.of("valueOf", "values"),
        List.of("valueOf", "values", "readObjectNoData", "compareTo", "describeConstable", "readObject",
            "getDeclaringClass", "hashCode", "equals", "name", "clone", "toString", "finalize", "ordinal"));
  }

  private void testEnumWithOneMethod(
      String className, String methodName, Consumer<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String enumClassName = "tech.intellispaces.framework.javastatements.samples." + className;
    TypeElement typeElement = getTestElement("enums/" + className + ".java");
    Session session = SessionBuilder.buildSession();

    // When
    EnumStatement enumStatement = JavaStatements.enumStatement(typeElement, session);

    // Then
    assertThat(enumStatement.simpleName()).isEqualTo(className);
    assertThat(enumStatement.canonicalName()).isEqualTo(enumClassName);

    assertThat(enumStatement.typeParameters()).isEmpty();
    assertThat(enumStatement.implementedInterfaces()).isEmpty();

    assertThat(enumStatement.parentTypes()).hasSize(1);
    assertThat(enumStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Enum.class.getCanonicalName());

    assertThat(enumStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder(methodName, "values", "valueOf");

    assertThat(enumStatement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder(methodName,
        "values",
        "valueOf",
        "name",
        "ordinal",
        "hashCode",
        "equals",
        "clone",
        "toString",
        "finalize",
        "compareTo",
        "readObject",
        "describeConstable",
        "getDeclaringClass",
        "readObjectNoData"
    );

    List<MethodStatement> declaredMethods = enumStatement.declaredMethodsWithName(methodName);
    assertThat(declaredMethods).hasSize(1);
    methodValidator.accept(declaredMethods.get(0));

    List<MethodStatement> actualMethods = enumStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.accept(actualMethods.get(0));

    assertThat(enumStatement.annotations()).hasSize(1);
    HandleFunctions.handle(enumStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(enumStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports, "tech.intellispaces.framework.javastatements.support.TesteeType")
    );

    assertThat(session.getType(enumClassName)).isSameAs(enumStatement);
  }
}
