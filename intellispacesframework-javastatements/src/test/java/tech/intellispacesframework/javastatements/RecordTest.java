package tech.intellispacesframework.javastatements;

import tech.intellispacesframework.commons.action.Handler;
import tech.intellispacesframework.commons.collection.CollectionFunctions;
import tech.intellispacesframework.commons.datahandle.HandleFunctions;
import tech.intellispacesframework.javastatements.session.SessionBuilder;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;
import tech.intellispacesframework.javastatements.statement.custom.MethodStatement;
import tech.intellispacesframework.javastatements.statement.custom.RecordStatement;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.support.TesteeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RecordStatement}.
 */
public class RecordTest extends AbstractCustomTypeTest {

  @Test
  public void testEmptyRecord() {
    // Given
    TypeElement typeElement = getTestElement("record/EmptyRecord.java");

    // When
    CustomType customTypeStatement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customTypeStatement).isInstanceOf(RecordStatement.class);
    RecordStatement recordStatement = customTypeStatement.asRecord().orElse(null);
    assertThat(recordStatement).isNotNull();

    assertThat(recordStatement.isAbstract()).isFalse();
    assertThat(recordStatement.simpleName()).isEqualTo("EmptyRecord");
    assertThat(recordStatement.canonicalName()).isEqualTo("tech.intellispacesframework.javastatements.samples.EmptyRecord");

    assertThat(recordStatement.typeParameters()).isEmpty();
    assertThat(recordStatement.implementedInterfaces()).isEmpty();

    assertThat(recordStatement.parentTypes()).hasSize(1);
    assertThat(recordStatement.parentTypes().get(0).targetType().canonicalName()).isEqualTo(Record.class.getCanonicalName());

    assertThat(recordStatement.declaredMethods()).hasSize(3);
    assertThat(recordStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder("equals", "hashCode", "toString");

    assertThat(recordStatement.annotations()).hasSize(1);
    HandleFunctions.handle(recordStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(recordStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispacesframework.javastatements.support.TesteeType"
    );
  }

  @Test
  public void testRecordImplementedTwoInterfaces() {
    // Given
    final var interface1Name = "tech.intellispacesframework.javastatements.samples.RecordImplementedTwoInterfaces.Interface1";
    final var interface2Name = "tech.intellispacesframework.javastatements.samples.RecordImplementedTwoInterfaces.Interface2";
    TypeElement typeElement = getTestElement("record/RecordImplementedTwoInterfaces.java");

    // When
    RecordStatement recordStatement = JavaStatements.recordStatement(typeElement);

    // Then
    assertThat(recordStatement).isNotNull();
    assertThat(recordStatement.simpleName()).isEqualTo("TesteeRecord");
    assertThat(recordStatement.canonicalName()).isEqualTo("tech.intellispacesframework.javastatements.samples.RecordImplementedTwoInterfaces.TesteeRecord");

    assertThat(recordStatement.typeParameters()).isEmpty();

    assertThat(recordStatement.implementedInterfaces()).hasSize(2);
    HandleFunctions.handle(recordStatement.implementedInterfaces().get(0), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(recordStatement.implementedInterfaces().get(1), implInterface -> {
      assertThat(implInterface.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(implInterface.typeArguments()).isEmpty();
    });

    assertThat(recordStatement.parentTypes()).hasSize(3);
    HandleFunctions.handle(recordStatement.parentTypes().get(0), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(Record.class.getCanonicalName());
      assertThat(parentType.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(recordStatement.parentTypes().get(1), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface1Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });
    HandleFunctions.handle(recordStatement.parentTypes().get(2), parentType -> {
      assertThat(parentType.targetType().canonicalName()).isEqualTo(interface2Name);
      assertThat(parentType.typeArguments()).isEmpty();
    });

    assertThat(recordStatement.declaredMethods()).hasSize(3);
    assertThat(recordStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder("equals", "hashCode", "toString");

    assertThat(recordStatement.annotations()).hasSize(1);
    HandleFunctions.handle(recordStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(recordStatement.dependencyTypenames()).containsExactlyInAnyOrder(
        "tech.intellispacesframework.javastatements.support.TesteeType"
    );

    assertThat(recordStatement.implementedInterfaces().get(0).referenceDeclaration()).isEqualTo("Interface1");
    assertThat(recordStatement.implementedInterfaces().get(1).referenceDeclaration()).isEqualTo("Interface2");
  }

  @Test
  public void testRecordWithSimpleMethod() {
    testRecordWithOneMethod("RecordWithSimpleMethod", "simpleMethod", this::validateSimpleMethod, List.of());
  }

  @Test
  public void testRecordWithMethodThrowsTwoExceptions() {
    testRecordWithOneMethod("RecordWithMethodThrowsTwoExceptions", "methodThrowsTwoExceptions", this::validateMethodThrowsTwoExceptions,
        List.of(IOException.class.getCanonicalName()));
  }

  @Test
  public void testRecordWithStaticMethod() {
    testRecordWithOneMethod("RecordWithStaticMethod", "staticMethod", this::validateStaticMethod, List.of());
  }

  @Test
  public void testRecordWithMethodUsingLocalTypeParameter() {
    testRecordWithOneMethod("RecordWithMethodUsingLocalTypeParameter", "methodUsingLocalTypeParameter", this::validateMethodUsingLocalTypeParameter,
        List.of(List.class.getCanonicalName()));
  }

  @Test
  public void testRecordWithMethodUsingWildcard() {
    testRecordWithOneMethod("RecordWithMethodUsingWildcard", "methodUsingWildcard", this::validateMethodUsingWildcard,
        List.of(List.class.getCanonicalName(), Collection.class.getCanonicalName()));
  }

  @Test
  public void testRecordWithMethodUsingWildcardThatExtendsOtherClass() {
    testRecordWithOneMethod("RecordWithMethodUsingWildcardThatExtendsOtherClass", "methodUsingWildcardThatExtendsOtherClass", this::validateMethodUsingWildcardThatExtendsOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testRecordWithMethodUsingWildcardThatSuperOtherClass() {
    testRecordWithOneMethod("RecordWithMethodUsingWildcardThatSuperOtherClass", "methodUsingWildcardThatSuperOtherClass", this::validateMethodUsingWildcardThatSuperOtherClass,
        List.of(Collection.class.getCanonicalName()));
  }

  @Test
  public void testRecordWithByteGetter() {
    testRecordWithOneMethod("RecordWithByteGetter", "byteGetter", this::validateByteGetter, List.of());
  }

  @Test
  public void testRecordWithShortGetter() {
    testRecordWithOneMethod("RecordWithShortGetter", "shortGetter", this::validateShortGetter, List.of());
  }

  @Test
  public void testRecordWithIntGetter() {
    testRecordWithOneMethod("RecordWithIntGetter", "intGetter", this::validateIntGetter, List.of());
  }

  @Test
  public void testRecordWithLongGetter() {
    testRecordWithOneMethod("RecordWithLongGetter", "longGetter", this::validateLongGetter, List.of());
  }

  @Test
  public void testRecordWithFloatGetter() {
    testRecordWithOneMethod("RecordWithFloatGetter", "floatGetter", this::validateFloatGetter, List.of());
  }

  @Test
  public void testRecordWithDoubleGetter() {
    testRecordWithOneMethod("RecordWithDoubleGetter", "doubleGetter", this::validateDoubleGetter, List.of());
  }

  @Test
  public void testRecordWithCharGetter() {
    testRecordWithOneMethod("RecordWithCharGetter", "charGetter", this::validateCharGetter, List.of());
  }

  @Test
  public void testRecordWithBooleanGetter() {
    testRecordWithOneMethod("RecordWithBooleanGetter", "booleanGetter", this::validateBooleanGetter, List.of());
  }

  @Test
  public void testRecordWithStringGetter() {
    testRecordWithOneMethod("RecordWithStringGetter", "stringGetter", this::validateStringGetter, List.of());
  }

  @Test
  public void testRecordWithArrayOfIntGetter() {
    testRecordWithOneMethod("RecordWithArrayOfIntGetter", "arrayOfIntGetter", this::validateArrayOfIntGetter, List.of());
  }

  @Test
  public void testRecordWithDoubleArrayOfStringGetter() {
    testRecordWithOneMethod("RecordWithDoubleArrayOfStringGetter", "doubleArrayOfStringGetter", this::validateDoubleArrayOfStringGetter, List.of());
  }

  @Test
  public void testRecordWithEnumGetter() {
    testRecordWithOneMethod("RecordWithEnumGetter", "enumGetter", this::validateEnumGetter, List.of());
  }

  @Test
  public void testRecordWithRecordGetter() {
    testRecordWithOneMethod("RecordWithRecordGetter", "recordGetter", this::validateRecordGetter, List.of());
  }

  @Test
  public void testRecordWithImplementedMethodFromInterface() {
    testCustomTypeWithImplementedMethodFromInterface("record/RecordWithImplementedMethodFromInterface.java",
        List.of("hashCode", "equals", "toString"),
        List.of("hashCode", "equals", "toString"));
  }

  @Test
  public void testRecordWithInheritedDefaultMethodFromInterface() {
    testCustomTypeWithInheritedDefaultMethodFromInterface("record/RecordWithInheritedDefaultMethodFromInterface.java",
        List.of("hashCode", "equals", "toString"),
        List.of("hashCode", "equals", "toString"));
  }

  @Test
  public void testRecordWithImplementedMethod() {
    testCustomerTypeWithOverrideMethod("record/RecordWithImplementedMethod.java",
        List.of("hashCode", "equals", "toString"),
        List.of("hashCode", "equals", "toString"));
  }

  @Test
  public void testRecordWithOverrideMethodAndNarrowedReturnType() {
    testCustomTypeWithOverrideMethodAndNarrowedReturnType("record/RecordWithOverrideMethodAndNarrowedReturnType.java",
        List.of("hashCode", "equals", "toString"),
        List.of("hashCode", "equals", "toString"));
  }

  @Test
  public void testGenericRecordWithCyclicTypeDependencyCase1() {
    // Given
    TypeElement typeElement = getTestElement("record/GenericRecordWithCyclicTypeDependencyCase1.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("GenericRecordWithCyclicTypeDependencyCase1");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("GenericRecordWithCyclicTypeDependencyCase1<T>");

    Assertions.assertThat(typeReference.targetType().asRecord()).isPresent();
    RecordStatement recordStatement = typeReference.targetType().asRecord().orElseThrow();

    assertThat(recordStatement.typeParameters()).hasSize(1);
    HandleFunctions.handle(recordStatement.typeParameters().get(0), typeParam -> {
      assertThat(typeParam.name()).isEqualTo("T");
      assertThat(typeParam.extendedBounds()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType()).isSameAs(recordStatement);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(typeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().typeArguments().get(0)
          .asNamedTypeReference().orElseThrow().name()).isEqualTo("T");
    });
  }

  @Test
  public void testGenericRecordWithCyclicTypeDependencyCase2() {
    // Given
    TypeElement typeElement = getTestElement("record/GenericRecordWithCyclicTypeDependencyCase2.java");

    // When
    CustomTypeReference typeReference = JavaStatements.customTypeReference(typeElement);

    // Then
    assertThat(typeReference.referenceDeclaration()).isEqualTo("RecordA");
    assertThat(typeReference.typeFullDeclaration()).isEqualTo("RecordA<T1 extends RecordB<?>>");
    assertThat(typeReference.typeBriefDeclaration()).isEqualTo("RecordA<T1>");

    Assertions.assertThat(typeReference.targetType().asRecord()).isPresent();
    RecordStatement recordStatement = typeReference.targetType().asRecord().orElseThrow();

    assertThat(recordStatement.typeParameters()).hasSize(1);
    HandleFunctions.handle(recordStatement.typeParameters().get(0), classATypeParam -> {
      assertThat(classATypeParam.name()).isEqualTo("T1");
      assertThat(classATypeParam.extendedBounds()).hasSize(1);
      HandleFunctions.handle(classATypeParam.extendedBounds().get(0).asCustomTypeReference().orElseThrow().targetType(), classBExtendedBound -> {
        assertThat(classBExtendedBound.canonicalName()).isEqualTo("tech.intellispacesframework.javastatements.samples.GenericRecordWithCyclicTypeDependencyCase2.RecordB");
        assertThat(classBExtendedBound.typeParameters()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().name()).isEqualTo("T2");
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds()).hasSize(1);
        assertThat(classBExtendedBound.typeParameters().get(0).asNamedTypeReference().orElseThrow().extendedBounds().get(0)
            .asCustomTypeReference().orElseThrow().targetType()).isSameAs(recordStatement);
      });
    });
  }

  private void testRecordWithOneMethod(
      String recordName, String methodName, Handler<MethodStatement> methodValidator, List<String> additionalImports
  ) {
    // Given
    String canonicalClassName = "tech.intellispacesframework.javastatements.samples." + recordName;
    TypeElement typeElement = getTestElement("record/" + recordName + ".java");
    Session session = SessionBuilder.buildSession();

    // When
    RecordStatement recordStatement = JavaStatements.recordStatement(typeElement, session);

    // Then
    assertThat(recordStatement.simpleName()).isEqualTo(recordName);
    assertThat(recordStatement.canonicalName()).isEqualTo(canonicalClassName);

    assertThat(recordStatement.typeParameters()).isEmpty();
    assertThat(recordStatement.implementedInterfaces()).isEmpty();
    assertThat(recordStatement.parentTypes()).hasSize(1);
    assertThat(recordStatement.parentTypes().get(0).asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Record.class.getCanonicalName());

    assertThat(recordStatement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder(methodName, "hashCode", "equals", "toString");

    assertThat(recordStatement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder(methodName, "hashCode", "equals", "toString");

    List<MethodStatement> declaredMethods = recordStatement.declaredMethodsWithName(methodName);
    assertThat(declaredMethods).hasSize(1);
    methodValidator.execute(declaredMethods.get(0));

    List<MethodStatement> actualMethods = recordStatement.actualMethodsWithName(methodName);
    assertThat(actualMethods).hasSize(1);
    methodValidator.execute(actualMethods.get(0));

    assertThat(recordStatement.annotations()).hasSize(1);
    HandleFunctions.handle(recordStatement.annotations().get(0), annInstance -> {
      assertThat(annInstance.annotationStatement().canonicalName()).isEqualTo(TesteeType.class.getCanonicalName());
      assertThat(annInstance.elements()).isEmpty();
    });

    assertThat(recordStatement.dependencyTypenames()).containsExactlyInAnyOrderElementsOf(
        CollectionFunctions.join(additionalImports, "tech.intellispacesframework.javastatements.support.TesteeType")
    );

    assertThat(session.getType(canonicalClassName)).isSameAs(recordStatement);
  }
}
