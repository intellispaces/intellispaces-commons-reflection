package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.method.MethodFunctions;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract adapter of {@link Class} to {@link CustomStatement}.
 */
abstract class AbstractCustomStatementBasedLandClass implements CustomStatement {
  protected final Class<?> aClass;

  AbstractCustomStatementBasedLandClass(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public String canonicalName() {
    return aClass.getCanonicalName();
  }

  @Override
  public String className() {
    return aClass.getName();
  }

  @Override
  public String simpleName() {
    return aClass.getSimpleName();
  }

  @Override
  public String packageName() {
    return aClass.getPackageName();
  }

  @Override
  public boolean isNested() {
    return aClass.isMemberClass();
  }

  @Override
  public List<NamedType> typeParameters() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public String typeParametersFullDeclaration() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public String typeParametersBriefDeclaration() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<CustomType> parentTypes() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public boolean hasParent(Class<?> parent) {
    return hasParent(parent.getCanonicalName());
  }

  @Override
  public boolean hasParent(CustomStatement parent) {
    return hasParent(parent.canonicalName());
  }

  @Override
  public boolean hasParent(String parentCanonicalName) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<AnnotationInstance> annotations() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethods() {
    return Arrays.stream(aClass.getDeclaredMethods())
        .map(MethodFunctions::getMethod)
        .toList();
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Optional<MethodStatement> declaredMethod(String name, List<Type> parameterTypes) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethods() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Optional<MethodStatement> actualMethod(String name, List<Type> parameterTypes) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Collection<CustomStatement> dependencies() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Collection<String> dependencyTypenames() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
