package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.javastatements.statement.method.MethodStatement;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract custom type statement.
 */
public interface CustomType extends AnnotatedStatement {

  boolean isAbstract();

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

  /**
   * Declared type parameters.
   *
   * <p>Some types have not a type parameters.
   */
  List<NamedReference> typeParameters();

  /**
   * Full declaration of the type parameters.
   */
  String typeParametersFullDeclaration();

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

  Optional<MethodStatement> declaredMethod(String name, List<TypeReference> parameterTypeReferences);

  /**
   * Actual methods.
   */
  List<MethodStatement> actualMethods();

  List<MethodStatement> actualMethodsWithName(String name);

  Optional<MethodStatement> actualMethod(String name, List<TypeReference> parameterTypeReferences);

  /**
   * Related class.
   */
  default Optional<ClassType> asClass() {
    return Optional.empty();
  }

  /**
   * Related interface.
   */
  default Optional<InterfaceType> asInterface() {
    return Optional.empty();
  }

  /**
   * Related record.
   */
  default Optional<RecordType> asRecord() {
    return Optional.empty();
  }

  /**
   * Related enum.
   */
  default Optional<EnumType> asEnum() {
    return Optional.empty();
  }

  /**
   * Related annotation.
   */
  default Optional<AnnotationType> asAnnotation() {
    return Optional.empty();
  }

  Collection<CustomType> dependencies();

  Collection<String> dependencyTypenames();
}
