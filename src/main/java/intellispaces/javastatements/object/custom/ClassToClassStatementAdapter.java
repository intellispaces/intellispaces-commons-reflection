package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;

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
  public String canonicalName() {
    return aClass.getCanonicalName();
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
  public Optional<CustomTypeReference> extendedClass() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> parentTypes() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<AnnotationInstance> annotations() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethods() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethods() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public Collection<CustomType> dependencies() {
    throw new JavaStatementException("Not implemented yet");
  }

  @Override
  public Collection<String> dependencyTypenames() {
    throw new JavaStatementException("Not implemented yet");
  }
}
