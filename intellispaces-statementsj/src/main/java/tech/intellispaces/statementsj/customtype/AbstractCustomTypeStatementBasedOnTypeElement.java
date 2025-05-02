package tech.intellispaces.statementsj.customtype;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.type.ElementFunctions;
import tech.intellispaces.statementsj.dependencies.DependenciesFunctions;
import tech.intellispaces.statementsj.common.JavaModelFunctions;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.context.TypeContextBuilder;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.instance.AnnotationInstance;
import tech.intellispaces.statementsj.method.MethodStatement;
import tech.intellispaces.statementsj.reference.CustomTypeReference;
import tech.intellispaces.statementsj.reference.NamedReference;
import tech.intellispaces.statementsj.reference.TypeReference;
import tech.intellispaces.statementsj.reference.TypeReferenceFunctions;
import tech.intellispaces.statementsj.session.Session;
import tech.intellispaces.statementsj.session.Sessions;

/**
 * Abstract adapter of {@link TypeElement} to {@link CustomType}.
 */
abstract class AbstractCustomTypeStatementBasedOnTypeElement implements CustomType {
  private final TypeElement typeElement;
  private final List<NamedReference> typeParams;
  private final SupplierAction<String> typeParametersFullDeclarationGetter;
  private final SupplierAction<String> typeParametersBriefDeclarationGetter;
  private final SupplierAction<List<CustomTypeReference>> parentTypesGetter;
  private final SupplierAction<List<AnnotationInstance>> annotationsGetter;
  private final SupplierAction<List<MethodStatement>> declaredMethodsGetter;
  private final SupplierAction<List<MethodStatement>> actualMethodsGetter;
  private final SupplierAction<Collection<CustomType>> dependenciesGetter;
  private final SupplierAction<Collection<String>> dependencyTypesGetter;
  private final TypeContext customTypeContext;

  AbstractCustomTypeStatementBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = JavaModelFunctions.getTypeParameters(typeElement, typeContext, session);
    this.customTypeContext = createNameContext(typeContext, this.typeParams);

    this.typeParametersFullDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.parentTypesGetter = CachedSupplierActions.get(JavaModelFunctions::getParentTypes, typeElement, typeContext, session);
    this.annotationsGetter = CachedSupplierActions.get(JavaModelFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = CachedSupplierActions.get(JavaModelFunctions::getDeclaredMethods, typeElement, this, customTypeContext, session);
    this.actualMethodsGetter = CachedSupplierActions.get(CustomTypeFunctions::getActualMethods, this, customTypeContext, session);
    this.dependenciesGetter = CachedSupplierActions.get(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = CachedSupplierActions.get(AbstractCustomTypeStatementBasedOnTypeElement::collectDependencyTypenames, this);
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
  public String typeParametersFullDeclaration(Function<String, String> nameMapper) {
    return CustomTypeFunctions.getTypeParametersDeclaration(this, true, nameMapper);
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
