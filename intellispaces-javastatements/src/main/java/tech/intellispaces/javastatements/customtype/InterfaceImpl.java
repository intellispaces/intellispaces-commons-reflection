package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.commons.type.TypeFunctions;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.common.DependenciesFunctions;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.instance.AnnotationInstance;
import tech.intellispaces.javastatements.method.MethodStatement;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.NamedReference;
import tech.intellispaces.javastatements.reference.TypeReference;
import tech.intellispaces.javastatements.reference.TypeReferenceFunctions;
import tech.intellispaces.javastatements.session.Sessions;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class InterfaceImpl implements InterfaceType {
  private String canonicalName;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private List<CustomTypeReference> parentTypes;
  private List<MethodStatement> declaredMethods;
  private final Getter<String> typeParametersFullDeclarationGetter;
  private final Getter<String> typeParametersBriefDeclarationGetter;
  private final Getter<List<MethodStatement>> actualMethodsGetter;
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;

  InterfaceImpl() {
    this.typeParametersFullDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.actualMethodsGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get());
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(InterfaceImpl::collectDependencyTypenames, this);
  }

  InterfaceImpl(
      String canonicalName,
      List<AnnotationInstance> annotations,
      List<NamedReference> typeParameters,
      List<CustomTypeReference> parentTypes,
      List<MethodStatement> declaredMethods
  ) {
    this();
    this.canonicalName = canonicalName;
    this.annotations = annotations;
    this.typeParameters = typeParameters;
    this.parentTypes = parentTypes;
    this.declaredMethods = declaredMethods;
  }

  void setCanonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
  }

  void setAnnotations(List<AnnotationInstance> annotations) {
    this.annotations = annotations;
  }

  void setTypeParameters(List<NamedReference> typeParameters) {
    this.typeParameters = typeParameters;
  }

  void setParentTypes(List<CustomTypeReference> parentTypes) {
    this.parentTypes = parentTypes;
  }

  void setDeclaredMethods(List<MethodStatement> declaredMethods) {
    this.declaredMethods = declaredMethods;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }

  @Override
  public boolean isAbstract() {
    return true;
  }
  @Override
  public String canonicalName() {
    return canonicalName;
  }

  @Override
  public String className() {
    return canonicalName;
  }

  @Override
  public String simpleName() {
    return TypeFunctions.getSimpleName(canonicalName());
  }

  @Override
  public String packageName() {
    return TypeFunctions.getPackageName(canonicalName());
  }

  @Override
  public boolean isNested() {
    return false;
  }

  @Override
  public List<AnnotationInstance> annotations() {
    return annotations;
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
  public List<NamedReference> typeParameters() {
    return typeParameters;
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
    return parentTypes;
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
  public List<MethodStatement> declaredMethods() {
    return declaredMethods;
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

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
