package tech.intellispaces.reflection.customtype;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.common.DependenciesFunctions;
import tech.intellispaces.reflection.context.TypeContexts;
import tech.intellispaces.reflection.instance.AnnotationInstance;
import tech.intellispaces.reflection.method.MethodStatement;
import tech.intellispaces.reflection.reference.CustomTypeReference;
import tech.intellispaces.reflection.reference.NamedReference;
import tech.intellispaces.reflection.reference.TypeReference;
import tech.intellispaces.reflection.reference.TypeReferenceFunctions;
import tech.intellispaces.reflection.session.Sessions;

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
    this.typeParametersFullDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.actualMethodsGetter = CachedSupplierActions.get(CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get());
    this.dependenciesGetter = CachedSupplierActions.get(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = CachedSupplierActions.get(InterfaceImpl::collectDependencyTypenames, this);
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
  public String typeParametersFullDeclaration(Function<String, String> nameMapper) {
    return CustomTypeFunctions.getTypeParametersDeclaration(this, true, nameMapper);
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
