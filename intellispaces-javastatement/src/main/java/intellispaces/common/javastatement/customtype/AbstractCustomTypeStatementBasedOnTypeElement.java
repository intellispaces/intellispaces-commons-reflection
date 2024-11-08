package intellispaces.common.javastatement.customtype;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.base.exception.NotImplementedExceptions;
import intellispaces.common.base.type.ElementFunctions;
import intellispaces.common.javastatement.common.DependenciesFunctions;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.context.TypeContextBuilder;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.TypeReference;
import intellispaces.common.javastatement.reference.TypeReferenceFunctions;
import intellispaces.common.javastatement.session.Session;
import intellispaces.common.javastatement.session.Sessions;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract adapter of {@link TypeElement} to {@link CustomType}.
 */
abstract class AbstractCustomTypeStatementBasedOnTypeElement implements CustomType {
  private final TypeElement typeElement;
  private final List<NamedReference> typeParams;
  private final Getter<String> typeParametersFullDeclarationGetter;
  private final Getter<String> typeParametersBriefDeclarationGetter;
  private final Getter<List<CustomTypeReference>> parentTypesGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;
  private final Getter<List<MethodStatement>> declaredMethodsGetter;
  private final Getter<List<MethodStatement>> actualMethodsGetter;
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final TypeContext customTypeContext;

  AbstractCustomTypeStatementBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = JavaModelFunctions.getTypeParameters(typeElement, typeContext, session);
    this.customTypeContext = createNameContext(typeContext, this.typeParams);

    this.typeParametersFullDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.parentTypesGetter = Actions.cachedLazyGetter(JavaModelFunctions::getParentTypes, typeElement, typeContext, session);
    this.annotationsGetter = Actions.cachedLazyGetter(JavaModelFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = Actions.cachedLazyGetter(JavaModelFunctions::getDeclaredMethods, typeElement, this, customTypeContext, session);
    this.actualMethodsGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, customTypeContext, session);
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(AbstractCustomTypeStatementBasedOnTypeElement::collectDependencyTypenames, this);
  }

  protected TypeContext customTypeContext() {
    return customTypeContext;
  }

  private TypeContext createNameContext(TypeContext parentContext, List<NamedReference> typeParams) {
    TypeContextBuilder builder = TypeContexts.build().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.get();
  }

  @Override
  public boolean isAbstract() {
    return ElementFunctions.isAbstractElement(typeElement);
  }

  @Override
  public boolean isFinal() {
    return ElementFunctions.isFinalElement(typeElement);
  }

  @Override
  public String canonicalName() {
    return JavaModelFunctions.getCanonicalName(typeElement);
  }

  @Override
  public String className() {
    return JavaModelFunctions.getClassName(typeElement);
  }

  @Override
  public String simpleName() {
    return JavaModelFunctions.getSimpleName(typeElement);
  }

  @Override
  public String packageName() {
    return JavaModelFunctions.getPackageName(typeElement);
  }

  @Override
  public boolean isNested() {
    Element enclosingElement = typeElement.getEnclosingElement();
    return enclosingElement.getKind().isClass() || enclosingElement.getKind().isInterface();
  }

  @Override
  public Optional<CustomType> enclosingType() {
    Element enclosingElement = typeElement.getEnclosingElement();
    if (enclosingElement.getKind().isClass()) {
      return Optional.of(Classes.of((TypeElement) enclosingElement, Sessions.get()));
    } else if (enclosingElement.getKind().isInterface()) {
      return Optional.of(Interfaces.of((TypeElement) enclosingElement, Sessions.get()));
    }
    return Optional.empty();
  }

  @Override
  public List<NamedReference> typeParameters() {
    return typeParams;
  }

  @Override
  public Map<String, NamedReference> typeParameterMap() {
    return typeParameters().stream().collect(Collectors.toMap(NamedReference::name, Function.identity()));
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
  public List<CustomTypeReference> parentTypes() {
    return parentTypesGetter.get();
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
  public Optional<MethodStatement> declaredMethod(String name, List<TypeReference> parameterTypes) {
    return declaredMethods().stream()
        .filter(m -> name.equals(m.name()))
        .filter(m -> m.params().size() == parameterTypes.size())
        .filter(m -> TypeReferenceFunctions.isEqualTypes(m.parameterTypes(), parameterTypes))
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
  public Optional<MethodStatement> actualMethod(String name, List<TypeReference> parameterTypes) {
    return actualMethods().stream()
        .filter(m -> name.equals(m.name()))
        .filter(m -> m.params().size() == parameterTypes.size())
        .filter(m -> TypeReferenceFunctions.isEqualTypes(m.parameterTypes(), parameterTypes))
        .findFirst();
  }

  @Override
  public Collection<CustomType> dependencies() {
    return dependenciesGetter.get();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.get();
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("4RNy5Q");
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
