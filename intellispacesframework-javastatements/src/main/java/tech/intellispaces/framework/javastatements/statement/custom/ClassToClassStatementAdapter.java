package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.type.TypeFunctions;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class ClassToClassStatementAdapter implements ClassStatement {
  private final Class<?> aClass;

  ClassToClassStatementAdapter(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public boolean isAbstract() {
    return TypeFunctions.isAbstractClass(aClass);
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
  public List<MethodStatement> constructors() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
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
  public List<CustomTypeReference> parentTypes() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public boolean hasParent(Class<?> aClass) {
    throw new UnsupportedOperationException("Not implemented yet");
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
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
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
  public Collection<CustomType> dependencies() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Collection<String> dependencyTypenames() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
