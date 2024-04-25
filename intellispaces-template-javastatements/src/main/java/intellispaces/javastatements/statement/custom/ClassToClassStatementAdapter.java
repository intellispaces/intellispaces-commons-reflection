package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.statement.instance.AnnotationInstance;
import intellispaces.javastatements.statement.reference.CustomTypeReference;
import intellispaces.javastatements.statement.reference.NamedTypeReference;

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
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> parentTypes() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<AnnotationInstance> annotations() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethods() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethods() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public Collection<CustomType> dependencies() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }

  @Override
  public Collection<String> dependencyTypenames() {
    throw JavaStatementException.withMessage("Not implemented yet");
  }
}
