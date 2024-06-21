package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.commons.type.TypeFunctions;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContextBuilder;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.DependencyFunctions;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;

import javax.lang.model.element.Element;
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
  private final TypeContext customTypeContext;

  CustomTypeStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = TypeElementFunctions.getTypeParameters(typeElement, typeContext, session);
    this.customTypeContext = createNameContext(typeContext, this.typeParams);

    this.typeParametersFullDeclarationGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, true);
    this.typeParametersBriefDeclarationGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getTypeParametersDeclaration, this, false);
    this.parentTypesGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getParentTypes, typeElement, typeContext, session);
    this.annotationsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getDeclaredMethods, typeElement, this, customTypeContext, session);
    this.actualMethodsGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, customTypeContext, session);
    this.dependenciesGetter = ActionBuilders.cachedLazyGetter(DependencyFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = ActionBuilders.cachedLazyGetter(CustomTypeStatementAdapter::collectDependencyTypenames, this);
  }

  protected TypeContext customTypeContext() {
    return customTypeContext;
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
  public boolean hasParent(Class<?> aClass) {
    return hasParent(aClass.getCanonicalName());
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
