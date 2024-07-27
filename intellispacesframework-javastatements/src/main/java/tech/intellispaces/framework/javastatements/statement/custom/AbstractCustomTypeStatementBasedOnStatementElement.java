package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContextBuilder;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.DependenciesFunctions;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.Type;
import tech.intellispaces.framework.javastatements.statement.type.TypeFunctions;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract adapter of {@link TypeElement} to {@link CustomStatement}.
 */
abstract class AbstractCustomTypeStatementBasedOnStatementElement implements CustomStatement {
  private final TypeElement typeElement;
  private final List<NamedType> typeParams;
  private final Getter<String> typeParametersFullDeclarationGetter;
  private final Getter<String> typeParametersBriefDeclarationGetter;
  private final Getter<List<CustomType>> parentTypesGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;
  private final Getter<List<MethodStatement>> declaredMethodsGetter;
  private final Getter<List<MethodStatement>> actualMethodsGetter;
  private final Getter<Collection<CustomStatement>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final TypeContext customTypeContext;

  AbstractCustomTypeStatementBasedOnStatementElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = TypeElementFunctions.getTypeParameters(typeElement, typeContext, session);
    this.customTypeContext = createNameContext(typeContext, this.typeParams);

    this.typeParametersFullDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.parentTypesGetter = Actions.cachedLazyGetter(TypeElementFunctions::getParentTypes, typeElement, typeContext, session);
    this.annotationsGetter = Actions.cachedLazyGetter(TypeElementFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = Actions.cachedLazyGetter(TypeElementFunctions::getDeclaredMethods, typeElement, this, customTypeContext, session);
    this.actualMethodsGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, customTypeContext, session);
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(AbstractCustomTypeStatementBasedOnStatementElement::collectDependencyTypenames, this);
  }

  protected TypeContext customTypeContext() {
    return customTypeContext;
  }

  private TypeContext createNameContext(TypeContext parentContext, List<NamedType> typeParams) {
    TypeContextBuilder builder = TypeContexts.build().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.get();
  }

  @Override
  public boolean isAbstract() {
    return tech.intellispaces.framework.commons.type.TypeFunctions.isAbstractElement(typeElement);
  }

  @Override
  public String canonicalName() {
    return TypeElementFunctions.getCanonicalName(typeElement);
  }

  @Override
  public String className() {
    return TypeElementFunctions.getClassName(typeElement);
  }

  @Override
  public String simpleName() {
    return TypeElementFunctions.getSimpleName(typeElement);
  }

  @Override
  public String packageName() {
    return TypeElementFunctions.getPackageName(typeElement);
  }

  @Override
  public boolean isNested() {
    Element enclosingElement = typeElement.getEnclosingElement();
    return enclosingElement.getKind().isClass() || enclosingElement.getKind().isInterface();
  }

  @Override
  public List<NamedType> typeParameters() {
    return typeParams;
  }

  @Override
  public String typeParametersFullDeclaration() {
    return typeParametersFullDeclarationGetter.get();
  }

  @Override
  public String typeParametersBriefDeclaration() {
    return typeParametersBriefDeclarationGetter.get();
  }

  @Override
  public List<CustomType> parentTypes() {
    return parentTypesGetter.get();
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
    return CustomTypeFunctions.hasParent(this, parentCanonicalName);
  }

  @Override
  public List<AnnotationInstance> annotations() {
    return annotationsGetter.get();
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return AnnotationFunctions.selectAnnotation(this, annotationClass);
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return AnnotationFunctions.selectAnnotation(this, annotationClass);
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return AnnotationFunctions.hasAnnotation(this, annotationClass);
  }

  @Override
  public List<MethodStatement> declaredMethods() {
    return declaredMethodsGetter.get();
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    return declaredMethods().stream()
        .filter(m -> name.equals(m.name()))
        .toList();
  }

  @Override
  public Optional<MethodStatement> declaredMethod(String name, List<Type> parameterTypes) {
    return declaredMethods().stream()
        .filter(m -> name.equals(m.name()))
        .filter(m -> m.params().size() == parameterTypes.size())
        .filter(m -> TypeFunctions.isEqualTypes(m.parameterTypes(), parameterTypes))
        .findFirst();
  }

  @Override
  public List<MethodStatement> actualMethods() {
    return actualMethodsGetter.get();
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    return actualMethods().stream()
        .filter(m -> name.equals(m.name()))
        .toList();
  }

  @Override
  public Optional<MethodStatement> actualMethod(String name, List<Type> parameterTypes) {
    return actualMethods().stream()
        .filter(m -> name.equals(m.name()))
        .filter(m -> m.params().size() == parameterTypes.size())
        .filter(m -> TypeFunctions.isEqualTypes(m.parameterTypes(), parameterTypes))
        .findFirst();
  }

  @Override
  public Collection<CustomStatement> dependencies() {
    return dependenciesGetter.get();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.get();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomStatement::canonicalName)
        .collect(Collectors.toSet());
  }
}
