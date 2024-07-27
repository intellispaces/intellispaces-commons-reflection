package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract custom statement.
 *
 * This type covers all custom types.
 */
public interface CustomStatement extends AnnotatedStatement {

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
  List<NamedType> typeParameters();

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
  List<CustomType> parentTypes();

  boolean hasParent(Class<?> parent);

  boolean hasParent(CustomStatement parent);

  boolean hasParent(String parentCanonicalName);

  /**
   * Declared methods.
   */
  List<MethodStatement> declaredMethods();

  List<MethodStatement> declaredMethodsWithName(String name);

  Optional<MethodStatement> declaredMethod(String name, List<Type> parameterTypes);

  /**
   * Actual methods.
   */
  List<MethodStatement> actualMethods();

  List<MethodStatement> actualMethodsWithName(String name);

  Optional<MethodStatement> actualMethod(String name, List<Type> parameterTypes);

  /**
   * Related class.
   */
  default Optional<ClassStatement> asClass() {
    return Optional.empty();
  }

  /**
   * Related interface.
   */
  default Optional<InterfaceStatement> asInterface() {
    return Optional.empty();
  }

  /**
   * Related record.
   */
  default Optional<RecordStatement> asRecord() {
    return Optional.empty();
  }

  /**
   * Related enum.
   */
  default Optional<EnumStatement> asEnum() {
    return Optional.empty();
  }

  /**
   * Related annotation.
   */
  default Optional<AnnotationStatement> asAnnotation() {
    return Optional.empty();
  }

  Collection<CustomStatement> dependencies();

  Collection<String> dependencyTypenames();
}
