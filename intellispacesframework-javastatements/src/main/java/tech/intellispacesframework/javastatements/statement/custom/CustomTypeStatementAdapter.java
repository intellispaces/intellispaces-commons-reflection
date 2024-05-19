package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.commons.type.TypeFunctions;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.context.TypeContextBuilder;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.DependencyFunctions;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class CustomTypeStatementAdapter implements CustomType {
  private final TypeElement typeElement;
  private final List<NamedTypeReference> typeParams;
  private final Getter<String> typeParametersFullDeclarationGetter;
  private final Getter<String> typeParametersBriefDeclarationGetter;
  private final Getter<List<CustomTypeReference>> parentTypesGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;
  private final Getter<List<MethodStatement>> declaredMethodsGetter;
  private final Getter<List<MethodStatement>> actualMethodsGetter;
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;

  CustomTypeStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = TypeElementFunctions.getTypeParameters(typeElement, typeContext, session);
    TypeContext classNameContext = createNameContext(typeContext, this.typeParams);

    this.typeParametersFullDeclarationGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.parentTypesGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getParentTypes, typeElement, typeContext, session);
    this.annotationsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getDeclaredMethods, typeElement, this, classNameContext, session);
    this.actualMethodsGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, classNameContext, session);
    this.dependenciesGetter = ActionBuilders.cachedLazyGetter(DependencyFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = ActionBuilders.cachedLazyGetter(CustomTypeStatementAdapter::collectDependencyTypenames, this);
  }

  private TypeContext createNameContext(TypeContext parentContext, List<NamedTypeReference> typeParams) {
    TypeContextBuilder builder = TypeContextBuilder.get().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.build();
  }

  @Override
  public boolean isAbstract() {
    return TypeFunctions.isAbstractElement(typeElement);
  }

  @Override
  public String canonicalName() {
    return TypeElementFunctions.getCanonicalName(typeElement);
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
  public List<NamedTypeReference> typeParameters() {
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
  public List<CustomTypeReference> parentTypes() {
    return parentTypesGetter.get();
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
