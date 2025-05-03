package tech.intellispaces.jstatements.customtype;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.collection.ArraysFunctions;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.jstatements.context.TypeContexts;
import tech.intellispaces.jstatements.instance.AnnotationInstance;
import tech.intellispaces.jstatements.instance.AnnotationInstances;
import tech.intellispaces.jstatements.method.MethodFunctions;
import tech.intellispaces.jstatements.method.MethodStatement;
import tech.intellispaces.jstatements.method.Methods;
import tech.intellispaces.jstatements.reference.CustomTypeReference;
import tech.intellispaces.jstatements.reference.CustomTypeReferences;
import tech.intellispaces.jstatements.reference.NamedReference;
import tech.intellispaces.jstatements.reference.NamedReferences;
import tech.intellispaces.jstatements.reference.ReferenceBound;
import tech.intellispaces.jstatements.reference.TypeReference;
import tech.intellispaces.jstatements.reference.TypeReferences;
import tech.intellispaces.jstatements.session.Sessions;

/**
 * Abstract adapter of {@link Class} to {@link CustomType}.
 */
abstract class AbstractCustomTypeBasedLandClass implements CustomType {
  protected final Class<?> aClass;
  private final SupplierAction<List<MethodStatement>> actualMethodsGetter;

  AbstractCustomTypeBasedLandClass(Class<?> aClass) {
    this.aClass = aClass;
    this.actualMethodsGetter = CachedSupplierActions.get(
        CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get()
    );
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
  public Optional<CustomType> enclosingType() {
    Class<?> enclosingClass = aClass.getEnclosingClass();
    if (enclosingClass == null) {
      return Optional.empty();
    }
    if (enclosingClass.isInterface()) {
      return Optional.of(Interfaces.of(enclosingClass));
    } else {
      return Optional.of(Classes.of(enclosingClass));
    }
  }

  @Override
  public List<NamedReference> typeParameters() {
    TypeVariable<? extends Class<?>>[] params = aClass.getTypeParameters();
    if (params.length == 0) {
      return List.of();
    }

    List<NamedReference> typeParameters = new ArrayList<>();
    for (TypeVariable<? extends Class<?>> param : params) {
      typeParameters.add(NamedReferences.get(
          param.getName(),
          this,
          Arrays.stream(param.getBounds())
              .map(b -> (ReferenceBound) TypeReferences.of(b))
              .toList()
      ));
    }
    return typeParameters;
  }

  @Override
  public Map<String, NamedReference> typeParameterMap() {
    return typeParameters().stream().collect(Collectors.toMap(NamedReference::name, Function.identity()));
  }

  @Override
  public String typeParametersFullDeclaration() {
    throw NotImplementedExceptions.withCode("bb+wMg");
  }

  @Override
  public String typeParametersFullDeclaration(Function<String, String> nameMapper) {
    throw NotImplementedExceptions.withCode("FMvXmbg4");
  }

  @Override
  public String typeParametersBriefDeclaration() {
    throw NotImplementedExceptions.withCode("9od2BA");
  }

  @Override
  public List<CustomTypeReference> parentTypes() {
    List<CustomTypeReference> parents = new ArrayList<>();
    if (aClass.getSuperclass() != null) {
      parents.add(CustomTypeReferences.get(aClass.getSuperclass()));
    }
    ArraysFunctions.foreach(aClass.getInterfaces(), i -> parents.add(CustomTypeReferences.get(i)));
    return parents;
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
    for (CustomTypeReference parent : parentTypes()) {
      if (parentCanonicalName.equals(parent.targetType().canonicalName())) {
        return true;
      }
    }
    for (CustomTypeReference parent : parentTypes()) {
      if (parent.targetType().hasParent(parentCanonicalName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<AnnotationInstance> annotations() {
    throw NotImplementedExceptions.withCode("55fafQ");
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    for (Annotation a : aClass.getAnnotations()) {
      if (a.annotationType().getCanonicalName().equals(annotationClass)) {
        return Optional.of(AnnotationInstances.of(a));
      }
    }
    return Optional.empty();
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return Optional.ofNullable(aClass.getAnnotation(annotationClass));
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return (aClass.getAnnotation(annotationClass) != null);
  }

  @Override
  public List<MethodStatement> declaredMethods() {
    return Arrays.stream(aClass.getDeclaredMethods())
        .filter(m -> !m.isBridge())
        .map(MethodFunctions::getMethod)
        .toList();
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    throw NotImplementedExceptions.withCode("kFoOiQ");
  }

  @Override
  public Optional<MethodStatement> declaredMethod(String name, List<TypeReference> parameterTypes) {
    var paramClasses = parameterTypes.stream()
        .map(r -> r.asCustomTypeReferenceOrElseThrow().targetClass())
        .toArray(Class<?>[]::new);
    try {
      Method method = aClass.getDeclaredMethod(name, paramClasses);
      return Optional.of(Methods.of(method));
    } catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<MethodStatement> actualMethods() {
    return actualMethodsGetter.get();
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    throw NotImplementedExceptions.withCode("v38ZbQ");
  }

  @Override
  public Optional<MethodStatement> actualMethod(String name, List<TypeReference> parameterTypes) {
    throw NotImplementedExceptions.withCode("EqL6XA");
  }

  @Override
  public Collection<CustomType> dependencies() {
    throw NotImplementedExceptions.withCode("qT1OxQ");
  }

  @Override
  public Collection<String> dependencyTypenames() {
    throw NotImplementedExceptions.withCode("tOOLKQ");
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("pfNdWQ");
  }
}
