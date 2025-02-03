package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.AnnotatedStatement;
import tech.intellispaces.commons.java.reflection.method.MethodStatement;
import tech.intellispaces.commons.java.reflection.reference.CustomTypeReference;
import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Abstract custom type statement.
 */
public interface CustomType extends AnnotatedStatement {

  boolean isAbstract();

  boolean isFinal();

  /**
   * Canonical class name.
   */
  String canonicalName();

  /**
   * Class name.
   *
   * For nested classes name includes the '$' character.
   */
  String className();

  /**
   * Simple class name.
   */
  String simpleName();

  /**
   * Package name.
   */
  String packageName();

  /**
   * Returns <code>true</code> if this type is nested or <code>false</code> otherwise.
   */
  boolean isNested();

  Optional<CustomType> enclosingType();

  /**
   * Declared type parameters.
   *
   * <p>Some types have not a type parameters.
   */
  List<NamedReference> typeParameters();

  Map<String, NamedReference> typeParameterMap();

  /**
   * Full declaration of the type parameters.
   */
  String typeParametersFullDeclaration();

  String typeParametersFullDeclaration(Function<String, String> nameMapper);

  /**
   * Brief declaration of the type parameters.
   */
  String typeParametersBriefDeclaration();

  /**
   * Declared type parent types.
   *
   * <p>Some types have not a parent types.
   */
  List<CustomTypeReference> parentTypes();

  boolean hasParent(Class<?> parent);

  boolean hasParent(CustomType parent);

  boolean hasParent(String parentCanonicalName);

  /**
   * Declared methods.
   */
  List<MethodStatement> declaredMethods();

  List<MethodStatement> declaredMethodsWithName(String name);

  Optional<MethodStatement> declaredMethod(String name, List<TypeReference> parameterTypes);

  /**
   * Actual methods.
   */
  List<MethodStatement> actualMethods();

  List<MethodStatement> actualMethodsWithName(String name);

  Optional<MethodStatement> actualMethod(String name, List<TypeReference> parameterTypes);

  /**
   * Related class.
   */
  default Optional<ClassType> asClass() {
    return Optional.empty();
  }

  /**
   * Related class.
   */
  default ClassType asClassOrElseThrow() {
    return asClass().orElseThrow();
  }

  /**
   * Related interface.
   */
  default Optional<InterfaceType> asInterface() {
    return Optional.empty();
  }

  /**
   * Related interface.
   */
  default InterfaceType asInterfaceOrElseThrow() {
    return asInterface().orElseThrow();
  }

  /**
   * Related record.
   */
  default Optional<RecordType> asRecord() {
    return Optional.empty();
  }

  /**
   * Related record.
   */
  default RecordType asRecordOrElseThrow() {
    return asRecord().orElseThrow();
  }

  /**
   * Related enum.
   */
  default Optional<EnumType> asEnum() {
    return Optional.empty();
  }

  /**
   * Related enum.
   */
  default EnumType asEnumOrElseThrow() {
    return asEnum().orElseThrow();
  }

  /**
   * Related annotation.
   */
  default Optional<AnnotationType> asAnnotation() {
    return Optional.empty();
  }

  /**
   * Related annotation.
   */
  default AnnotationType asAnnotationOrElseThrow() {
    return asAnnotation().orElseThrow();
  }

  Collection<CustomType> dependencies();

  Collection<String> dependencyTypenames();
}
