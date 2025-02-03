package tech.intellispaces.commons.java.reflection;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import tech.intellispaces.commons.base.collection.CollectionFunctions;
import tech.intellispaces.commons.base.object.ObjectFunctions;
import tech.intellispaces.commons.java.reflection.customtype.CustomType;
import tech.intellispaces.commons.java.reflection.method.MethodStatement;
import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.PrimitiveReferences;
import tech.intellispaces.commons.java.reflection.samples.TestEnum;
import tech.intellispaces.commons.java.reflection.samples.TestRecord;
import tech.intellispaces.commons.java.reflection.support.TesteeTypeAnnotationProcessor;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractCustomStatementTest {

  protected TypeElement getTestElement(String sourceName) {
    var annotationProcessor = new TesteeTypeAnnotationProcessor();
    Compiler compiler = Compiler.javac().withProcessors(annotationProcessor);
    JavaFileObject file = JavaFileObjects.forResource(sourceName);

    Compilation compilation = compiler.compile(file);
    CompilationSubject.assertThat(compilation).succeeded();
    return annotationProcessor.testee();
  }

  protected void validateDefaultConstructor(MethodStatement method) {
    assertThat(method.name()).isEqualTo("<init>");
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isFalse();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }

  protected void validateSimpleMethod(MethodStatement method) {
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isFalse();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }

  protected void validateMethodThrowsTwoExceptions(MethodStatement method) {
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isFalse();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();

    assertThat(method.exceptions()).hasSize(2);
    assertThat(method.exceptions().get(0).asCustomTypeReference().orElseThrow()
        .targetType().canonicalName()).isEqualTo(IOException.class.getCanonicalName());
    assertThat(method.exceptions().get(1).asCustomTypeReference().orElseThrow()
        .targetType().canonicalName()).isEqualTo(ClassNotFoundException.class.getCanonicalName());
  }

  protected void validateStaticMethod(MethodStatement method) {
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isTrue();
    assertThat(method.isDefault()).isFalse();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }

  protected void validateMethodUsingWildcard(MethodStatement method) {
    assertThat(method.returnType()).isPresent();
    ObjectFunctions.handle(method.returnType().orElseThrow().asCustomTypeReference().orElseThrow(), returnType -> {
      assertThat(returnType.targetType().canonicalName()).isEqualTo(List.class.getCanonicalName());
      assertThat(returnType.targetType().className()).isEqualTo(List.class.getName());
      assertThat(returnType.typeArguments()).hasSize(1);
      assertThat(returnType.typeArguments().get(0).asWildcard()).isPresent();
      assertThat(returnType.typeArguments().get(0).asWildcard().orElseThrow().extendedBound()).isEmpty();
      assertThat(returnType.typeArguments().get(0).asWildcard().orElseThrow().superBound()).isEmpty();
      assertThat(returnType.actualDeclaration()).isEqualTo("java.util.List<?>");
    });

    assertThat(method.params()).hasSize(1);
    ObjectFunctions.handle(method.params().get(0), param -> {
      assertThat(param.name()).isEqualTo("arg");
      assertThat(param.type().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Collection.class.getCanonicalName());
      assertThat(param.type().asCustomTypeReference().orElseThrow().targetType().className()).isEqualTo(Collection.class.getName());
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments().get(0).asWildcard()).isPresent();
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments().get(0).asWildcard().orElseThrow().extendedBound()).isEmpty();
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments().get(0).asWildcard().orElseThrow().superBound()).isEmpty();
      assertThat(param.type().actualDeclaration()).isEqualTo("java.util.Collection<?>");
    });
  }

  protected void validateMethodUsingWildcardThatExtendsOtherClass(MethodStatement method) {
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).hasSize(1);
    ObjectFunctions.handle(method.params().get(0), param -> {
      assertThat(param.name()).isEqualTo("arg");
      assertThat(param.type().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Collection.class.getCanonicalName());
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      ObjectFunctions.handle(param.type().asCustomTypeReference().orElseThrow().typeArguments().get(0).asWildcard(), wildcard -> {
        assertThat(wildcard).isPresent();
        assertThat(wildcard.orElseThrow().extendedBound()).isPresent();
        assertThat(wildcard.orElseThrow().extendedBound().orElseThrow()
            .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Number.class.getCanonicalName());
        assertThat(wildcard.orElseThrow().superBound()).isEmpty();
      });
      assertThat(param.type().actualDeclaration()).isEqualTo("java.util.Collection<? extends java.lang.Number>");
    });
  }

  protected void validateMethodUsingWildcardThatSuperOtherClass(MethodStatement method) {
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).hasSize(1);
    ObjectFunctions.handle(method.params().get(0), param -> {
      assertThat(param.name()).isEqualTo("arg");
      assertThat(param.type().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Collection.class.getCanonicalName());
      assertThat(param.type().asCustomTypeReference().orElseThrow().typeArguments()).hasSize(1);
      ObjectFunctions.handle(param.type().asCustomTypeReference().orElseThrow().typeArguments().get(0).asWildcard(), wildcard -> {
        assertThat(wildcard).isPresent();
        assertThat(wildcard.orElseThrow().superBound()).isPresent();
        assertThat(wildcard.orElseThrow().superBound().orElseThrow()
            .asArrayReference().orElseThrow()
            .elementType().asCustomTypeReference().orElseThrow()
            .targetType().canonicalName()).isEqualTo(Number.class.getCanonicalName());
        assertThat(wildcard.orElseThrow().extendedBound()).isEmpty();
      });
      assertThat(param.type().actualDeclaration()).isEqualTo("java.util.Collection<? super java.lang.Number[]>");
    });
  }

  protected void validateMethodUsingLocalTypeParameter(MethodStatement method) {
    assertThat(method.typeParameters()).hasSize(1);
    NamedReference typeParamT = method.typeParameters().get(0);

    assertThat(method.returnType()).isPresent();
    ObjectFunctions.handle(method.returnType().orElseThrow().asCustomTypeReference().orElseThrow(), returnType -> {
      assertThat(returnType.targetType().canonicalName()).isEqualTo(List.class.getCanonicalName());
      assertThat(returnType.targetType().className()).isEqualTo(List.class.getName());
      assertThat(returnType.typeArguments()).hasSize(1);
      assertThat(returnType.typeArguments().get(0).asNamedReference()).isPresent();
      assertThat(returnType.typeArguments().get(0).asNamedReference().orElseThrow().name()).isEqualTo("T");
      assertThat(returnType.typeArguments().get(0).asNamedReference().orElseThrow().extendedBounds()).isEmpty();
      assertThat(returnType.typeArguments().get(0).asNamedReference().orElseThrow()).isSameAs(typeParamT);
      assertThat(returnType.actualDeclaration()).isEqualTo("java.util.List<T>");
    });

    assertThat(method.params()).hasSize(1);
    ObjectFunctions.handle(method.params().get(0), param -> {
      assertThat(param.name()).isEqualTo("arg");
      assertThat(param.type().asNamedReference()).isPresent();
      assertThat(param.type().asNamedReference().orElseThrow().name()).isEqualTo("T");
      assertThat(param.type().asNamedReference().orElseThrow().extendedBounds()).isEmpty();
      assertThat(param.type().asNamedReference().orElseThrow()).isSameAs(typeParamT);
      assertThat(param.type().actualDeclaration()).isEqualTo("T");
    });
  }

  protected void validateByteGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("byte");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractByteGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Byte);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("byte");
    assertThat(method.params()).isEmpty();
  }

  protected void validateShortGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("short");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractShortGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Short);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("short");
    assertThat(method.params()).isEmpty();
  }

  protected void validateIntGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("int");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractIntGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("int");
    assertThat(method.params()).isEmpty();
  }

  protected void validateLongGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("long");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractLongGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Long);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("long");
    assertThat(method.params()).isEmpty();
  }

  protected void validateFloatGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("float");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractFloatGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Float);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("float");
    assertThat(method.params()).isEmpty();
  }

  protected void validateDoubleGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("double");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractDoubleGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Double);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("double");
    assertThat(method.params()).isEmpty();
  }

  protected void validateCharGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("char");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractCharGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Char);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("char");
    assertThat(method.params()).isEmpty();
  }

  protected void validateBooleanGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("boolean");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractBooleanGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Boolean);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("boolean");
    assertThat(method.params()).isEmpty();
  }

  protected void validateStringGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("java.lang.String");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractStringGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("java.lang.String");
    assertThat(method.params()).isEmpty();
  }

  protected void validateArrayOfIntGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow()
        .asArrayReference().orElseThrow()
        .elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("int[]");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractArrayOfIntGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow()
        .asArrayReference().orElseThrow()
        .elementType().asPrimitiveReference().orElseThrow()).isSameAs(PrimitiveReferences.Int);
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("int[]");
    assertThat(method.params()).isEmpty();
  }

  protected void validateDoubleArrayOfStringGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow()
        .asArrayReference().orElseThrow()
        .elementType().asArrayReference().orElseThrow()
        .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("java.lang.String[][]");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractDoubleArrayOfStringGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow()
        .asArrayReference().orElseThrow()
        .elementType().asArrayReference().orElseThrow()
        .elementType().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("java.lang.String[][]");
    assertThat(method.params()).isEmpty();
  }

  protected void validateEnumGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("tech.intellispaces.commons.java.reflection.samples.TestEnum");
    assertThat(method.params()).isEmpty();
  }

  protected void validateAbstractEnumGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestEnum.class.getCanonicalName());
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("tech.intellispaces.commons.java.reflection.samples.TestEnum");
    assertThat(method.params()).isEmpty();
  }

  protected void validateRecordGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isFalse();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestRecord.class.getCanonicalName());
    assertThat(method.params()).isEmpty();
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("tech.intellispaces.commons.java.reflection.samples.TestRecord");
  }

  protected void validateAbstractRecordGetter(MethodStatement method) {
    assertThat(method.isAbstract()).isTrue();
    assertThat(method.returnType().orElseThrow()
        .asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(TestRecord.class.getCanonicalName());
    assertThat(method.params()).isEmpty();
    assertThat(method.returnType().orElseThrow().actualDeclaration()).isEqualTo("tech.intellispaces.commons.java.reflection.samples.TestRecord");
  }

  protected void testCustomTypeWithInheritedMethod(String filePath) {
    // Given
    TypeElement typeElement = getTestElement(filePath);

    // When
    CustomType statement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(statement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactly("method1");

    assertThat(statement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrder("method1", "method2");

    MethodStatement method1 = statement.actualMethodsWithName("method1").get(0);
    assertThat(method1.isPublic()).isTrue();
    assertThat(method1.isStatic()).isFalse();
    assertThat(method1.isDefault()).isFalse();
    assertThat(method1.typeParameters()).isEmpty();
    assertThat(method1.returnType()).isEmpty();
    assertThat(method1.params()).isEmpty();
    assertThat(method1.exceptions()).isEmpty();

    MethodStatement method2 = statement.actualMethodsWithName("method2").get(0);
    assertThat(method2.isPublic()).isTrue();
    assertThat(method2.isStatic()).isFalse();
    assertThat(method2.isDefault()).isFalse();
    assertThat(method2.typeParameters()).isEmpty();
    assertThat(method2.returnType()).isEmpty();
    assertThat(method2.params()).isEmpty();
    assertThat(method2.exceptions()).isEmpty();
  }

  protected void testCustomTypeWithImplementedMethodFromInterface(
      String filePath, List<String> additionalDeclaredMethods, List<String> additionalActualMethods
  ) {
    // Given
    TypeElement typeElement = getTestElement(filePath);

    // When
    CustomType statement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(statement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalDeclaredMethods, "method1", "method2"));

    assertThat(statement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalActualMethods, "method1", "method2"));

    MethodStatement method1 = statement.actualMethodsWithName("method1").get(0);
    assertThat(method1.isPublic()).isTrue();
    assertThat(method1.isStatic()).isFalse();
    assertThat(method1.isDefault()).isFalse();
    assertThat(method1.typeParameters()).isEmpty();
    assertThat(method1.returnType()).isEmpty();
    assertThat(method1.params()).isEmpty();
    assertThat(method1.exceptions()).isEmpty();

    MethodStatement method2 = statement.actualMethodsWithName("method2").get(0);
    assertThat(method2.isPublic()).isTrue();
    assertThat(method2.isStatic()).isFalse();
    assertThat(method2.isDefault()).isFalse();
    assertThat(method2.typeParameters()).isEmpty();
    assertThat(method2.returnType()).isEmpty();
    assertThat(method2.params()).isEmpty();
    assertThat(method2.exceptions()).isEmpty();
  }

  public void testCustomTypeWithInheritedDefaultMethodFromInterface(
      String filePath, List<String> additionalDeclaredMethods, List<String> additionalActualMethods
  ) {
    // Given
    TypeElement typeElement = getTestElement(filePath);

    // When
    CustomType statement = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(statement.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalDeclaredMethods, "method1"));

    assertThat(statement.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalActualMethods, "method1", "method2"));

    MethodStatement method1 = statement.actualMethodsWithName("method1").get(0);
    assertThat(method1.isPublic()).isTrue();
    assertThat(method1.isStatic()).isFalse();
    assertThat(method1.isDefault()).isFalse();
    assertThat(method1.typeParameters()).isEmpty();
    assertThat(method1.returnType()).isEmpty();
    assertThat(method1.params()).isEmpty();
    assertThat(method1.exceptions()).isEmpty();

    MethodStatement method2 = statement.actualMethodsWithName("method2").get(0);
    assertThat(method2.isPublic()).isTrue();
    assertThat(method2.isStatic()).isFalse();
    assertThat(method2.isDefault()).isTrue();
    assertThat(method2.typeParameters()).isEmpty();
    assertThat(method2.returnType()).isEmpty();
    assertThat(method2.params()).isEmpty();
    assertThat(method2.exceptions()).isEmpty();
  }

  public void testCustomerTypeWithOverrideMethod(
      String filePath, List<String> additionalDeclaredMethods, List<String> additionalActualMethods
  ) {
    // Given
    TypeElement typeElement = getTestElement(filePath);

    // When
    CustomType customType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customType.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalDeclaredMethods, "method"));

    assertThat(customType.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalActualMethods, "method"));

    MethodStatement method = customType.actualMethodsWithName("method").get(0);
    assertThat(method.isPublic()).isTrue();
    assertThat(method.isStatic()).isFalse();
    assertThat(method.isDefault()).isFalse();
    assertThat(method.typeParameters()).isEmpty();
    assertThat(method.returnType()).isEmpty();
    assertThat(method.params()).isEmpty();
    assertThat(method.exceptions()).isEmpty();
  }

  public void testCustomTypeWithOverrideMethodAndNarrowedReturnType(
      String filePath, List<String> additionalDeclaredMethods, List<String> additionalActualMethods
  ) {
    // Given
    TypeElement typeElement = getTestElement(filePath);

    // When
    CustomType customType = JavaStatements.customTypeStatement(typeElement);

    // Then
    assertThat(customType.declaredMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalDeclaredMethods, "method"));

    assertThat(customType.actualMethods().stream()
        .map(MethodStatement::name)
        .collect(Collectors.toSet())
    ).containsExactlyInAnyOrderElementsOf(CollectionFunctions.join(additionalActualMethods, "method"));

    MethodStatement method1 = customType.actualMethodsWithName("method").get(0);
    assertThat(method1.isPublic()).isTrue();
    assertThat(method1.isStatic()).isFalse();
    assertThat(method1.isDefault()).isFalse();
    assertThat(method1.typeParameters()).isEmpty();
    assertThat(method1.returnType().orElseThrow().asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    assertThat(method1.params()).isEmpty();
    assertThat(method1.exceptions()).isEmpty();
  }
}
