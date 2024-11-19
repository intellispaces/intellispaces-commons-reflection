package intellispaces.common.javastatement.customtype;

import tech.intellispaces.action.Actions;
import tech.intellispaces.action.cache.CacheActions;
import tech.intellispaces.action.supplier.SupplierAction;
import tech.intellispaces.entity.exception.NotImplementedExceptions;
import tech.intellispaces.entity.type.ClassNameFunctions;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.common.DependenciesFunctions;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.TypeReference;
import intellispaces.common.javastatement.reference.TypeReferenceFunctions;
import intellispaces.common.javastatement.session.Sessions;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class InterfaceImpl implements InterfaceType {
  private String canonicalName;
  private CustomType enclosingType;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private List<CustomTypeReference> parentTypes;
  private List<MethodStatement> declaredMethods;
  private final SupplierAction<String> typeParametersFullDeclarationGetter;
  private final SupplierAction<String> typeParametersBriefDeclarationGetter;
  private final SupplierAction<List<MethodStatement>> actualMethodsGetter;
  private final SupplierAction<Collection<CustomType>> dependenciesGetter;
  private final SupplierAction<Collection<String>> dependencyTypesGetter;

  InterfaceImpl() {
    this.typeParametersFullDeclarationGetter = CacheActions.cachedLazySupplierAction(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = CacheActions.cachedLazySupplierAction(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.actualMethodsGetter = CacheActions.cachedLazySupplierAction(CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get());
    this.dependenciesGetter = CacheActions.cachedLazySupplierAction(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = CacheActions.cachedLazySupplierAction(InterfaceImpl::collectDependencyTypenames, this);
  }

  InterfaceImpl(
      String canonicalName,
      CustomType enclosingType,
      List<AnnotationInstance> annotations,
      List<NamedReference> typeParameters,
      List<CustomTypeReference> parentTypes,
      List<MethodStatement> declaredMethods
  ) {
    this();
    this.canonicalName = canonicalName;
    this.enclosingType = enclosingType;
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
  public boolean isFinal() {
    return false;
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
    return ClassNameFunctions.getSimpleName(canonicalName());
  }

  @Override
  public String packageName() {
    return ClassNameFunctions.getPackageName(canonicalName());
  }

  @Override
  public boolean isNested() {
    return enclosingType != null;
  }

  @Override
  public Optional<CustomType> enclosingType() {
    return Optional.ofNullable(enclosingType);
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

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("sXN0Kw");
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
