package intellispaces.javastatements.customtype;

import intellispaces.javastatements.instance.AnnotationInstance;
import intellispaces.javastatements.method.MethodFunctions;
import intellispaces.javastatements.method.MethodStatement;
import intellispaces.javastatements.method.Methods;
import intellispaces.javastatements.reference.CustomTypeReference;
import intellispaces.javastatements.reference.NamedReference;
import intellispaces.javastatements.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract adapter of {@link Class} to {@link CustomType}.
 */
abstract class AbstractCustomTypeBasedLandClass implements CustomType {
  protected final Class<?> aClass;

  AbstractCustomTypeBasedLandClass(Class<?> aClass) {
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
  public List<NamedReference> typeParameters() {
    TypeVariable<? extends Class<?>>[] params = aClass.getTypeParameters();
    if (params.length == 0) {
      return List.of();
    }
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
  public boolean hasParent(Class<?> parent) {
    return hasParent(parent.getCanonicalName());
  }

  @Override
  public boolean hasParent(CustomType parent) {
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
    return Optional.ofNullable(aClass.getAnnotation(annotationClass));
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
  public Optional<MethodStatement> declaredMethod(String name, List<TypeReference> parameterTypes) {
    var paramClasses = (Class<?>[]) parameterTypes.stream()
        .map(r -> r.asCustomTypeReferenceOrElseThrow().targetClass())
        .toArray();
    try {
      Method method = aClass.getDeclaredMethod(name, paramClasses);
      return Optional.of(Methods.of(method));
    } catch (NoSuchMethodException e) {
      return Optional.empty();
    }
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
  public Optional<MethodStatement> actualMethod(String name, List<TypeReference> parameterTypes) {
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
