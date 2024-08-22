package intellispaces.javastatements.customtype;

import intellispaces.actions.Actions;
import intellispaces.actions.getter.Getter;
import intellispaces.commons.type.TypeFunctions;
import intellispaces.javastatements.StatementType;
import intellispaces.javastatements.StatementTypes;
import intellispaces.javastatements.common.DependenciesFunctions;
import intellispaces.javastatements.context.TypeContexts;
import intellispaces.javastatements.instance.AnnotationInstance;
import intellispaces.javastatements.method.MethodStatement;
import intellispaces.javastatements.reference.CustomTypeReference;
import intellispaces.javastatements.reference.NamedReference;
import intellispaces.javastatements.reference.TypeReference;
import intellispaces.javastatements.reference.TypeReferenceFunctions;
import intellispaces.javastatements.session.Sessions;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ClassTypeImpl implements ClassType {
  private boolean nested;
  private boolean isAbstract;
  private String canonicalName;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private CustomTypeReference extendedClass;
  private List<CustomTypeReference> implementedInterfaces;
  private List<MethodStatement> constructors;
  private List<MethodStatement> declaredMethods;

  private final Getter<String> typeParametersFullDeclarationGetter;
  private final Getter<String> typeParametersBriefDeclarationGetter;
  private final Getter<List<MethodStatement>> actualMethodsGetter;
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;

  ClassTypeImpl() {
    this.typeParametersFullDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.actualMethodsGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, TypeContexts.empty(), Sessions.get());
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(ClassTypeImpl::collectDependencyTypenames, this);
  }

  ClassTypeImpl(
      boolean nested,
      boolean isAbstract,
      String canonicalName,
      List<AnnotationInstance> annotations,
      List<NamedReference> typeParameters,
      CustomTypeReference extendedClass,
      List<CustomTypeReference> implementedInterfaces,
      List<MethodStatement> constructors,
      List<MethodStatement> declaredMethods
  ) {
    this();
    this.nested = nested;
    this.isAbstract = isAbstract;
    this.canonicalName = canonicalName;
    this.annotations = annotations;
    this.typeParameters = typeParameters;
    this.extendedClass = extendedClass;
    this.implementedInterfaces = implementedInterfaces;
    this.constructors = constructors;
    this.declaredMethods = declaredMethods;
  }

  @Override
  public boolean isNested() {
    return nested;
  }

  @Override
  public boolean isAbstract() {
    return isAbstract;
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
    return TypeFunctions.getSimpleName(canonicalName);
  }

  @Override
  public String packageName() {
    return TypeFunctions.getPackageName(canonicalName);
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
  public String typeParametersFullDeclaration() {
    return typeParametersFullDeclarationGetter.get();
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

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
