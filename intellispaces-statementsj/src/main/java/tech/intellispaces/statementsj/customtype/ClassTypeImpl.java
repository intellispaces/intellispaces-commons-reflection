package tech.intellispaces.statementsj.customtype;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.dependencies.DependenciesFunctions;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.instance.AnnotationInstance;
import tech.intellispaces.statementsj.method.MethodStatement;
import tech.intellispaces.statementsj.reference.CustomTypeReference;
import tech.intellispaces.statementsj.reference.NamedReference;
import tech.intellispaces.statementsj.reference.TypeReference;
import tech.intellispaces.statementsj.reference.TypeReferenceFunctions;
import tech.intellispaces.statementsj.session.Sessions;

class ClassTypeImpl implements ClassType {
  private boolean isAbstract;
  private boolean isFinal;
  private String canonicalName;
  private CustomType enclosingType;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private CustomTypeReference extendedClass;
  private List<CustomTypeReference> implementedInterfaces;
  private List<MethodStatement> constructors;
  private List<MethodStatement> declaredMethods;

  private final SupplierAction<String> typeParametersFullDeclarationGetter;
  private final SupplierAction<String> typeParametersBriefDeclarationGetter;
  private final SupplierAction<List<MethodStatement>> actualMethodsGetter;
  private final SupplierAction<Collection<CustomType>> dependenciesGetter;
  private final SupplierAction<Collection<String>> dependencyTypesGetter;

  ClassTypeImpl() {
    this.typeParametersFullDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = CachedSupplierActions.get(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.actualMethodsGetter = CachedSupplierActions.get(CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get());
    this.dependenciesGetter = CachedSupplierActions.get(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = CachedSupplierActions.get(ClassTypeImpl::collectDependencyTypenames, this);
  }

  ClassTypeImpl(
      boolean isAbstract,
      boolean isFinal,
      String canonicalName,
      CustomType enclosingType,
      List<AnnotationInstance> annotations,
      List<NamedReference> typeParameters,
      CustomTypeReference extendedClass,
      List<CustomTypeReference> implementedInterfaces,
      List<MethodStatement> constructors,
      List<MethodStatement> declaredMethods
  ) {
    this();
    this.isAbstract = isAbstract;
    this.isFinal = isFinal;
    this.canonicalName = canonicalName;
    this.enclosingType = enclosingType;
    this.annotations = annotations;
    this.typeParameters = typeParameters;
    this.extendedClass = extendedClass;
    this.implementedInterfaces = implementedInterfaces;
    this.constructors = constructors;
    this.declaredMethods = declaredMethods;
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
  public boolean isAbstract() {
    return isAbstract;
  }

  @Override
  public boolean isFinal() {
    return isFinal;
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
    return ClassNameFunctions.getSimpleName(canonicalName);
  }

  @Override
  public String packageName() {
    return ClassNameFunctions.getPackageName(canonicalName);
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
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public List<MethodStatement> constructors() {
    return constructors;
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    return Optional.ofNullable(extendedClass);
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfaces;
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
    List<CustomTypeReference> parents = new ArrayList<>();
    if (extendedClass != null) {
      parents.add(extendedClass);
    }
    parents.addAll(implementedInterfaces);
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
    throw NotImplementedExceptions.withCode("zZPnvg");
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
