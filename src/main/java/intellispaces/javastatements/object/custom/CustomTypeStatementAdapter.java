package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.AnnotationFunctions;
import intellispaces.javastatements.function.CustomTypeFunctions;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.function.DependencyFunctions;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.context.NameContextBuilder;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class CustomTypeStatementAdapter implements CustomType {
  private final TypeElement typeElement;
  private final List<NamedTypeReference> typeParams;
  private final GetterAction<List<CustomTypeReference>> parentTypesGetter;
  private final GetterAction<List<AnnotationInstance>> annotationsGetter;
  private final GetterAction<List<MethodStatement>> declaredMethodsGetter;
  private final GetterAction<List<MethodStatement>> actualMethodsGetter;
  private final GetterAction<Collection<CustomType>> dependenciesGetter;
  private final GetterAction<Collection<String>> dependencyTypesGetter;

  CustomTypeStatementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    this.typeElement = typeElement;
    this.typeParams = TypeFunctions.getTypeParameters(typeElement, nameContext, session);
    NameContext classNameContext = createNameContext(nameContext, this.typeParams);
    this.parentTypesGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getParentTypes, typeElement, nameContext, session);
    this.annotationsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getAnnotations, typeElement, session);
    this.declaredMethodsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getDeclaredMethods, typeElement, this, classNameContext, session);
    this.actualMethodsGetter = ActionFunctions.cachedLazyGetter(CustomTypeFunctions::getActualMethods, this, classNameContext, session);
    this.dependenciesGetter = ActionFunctions.cachedLazyGetter(DependencyFunctions::getCustomTypeDependencies, this);
    this.dependencyTypesGetter = ActionFunctions.cachedLazyGetter(CustomTypeStatementAdapter::collectDependencyTypenames, this);
  }

  private NameContext createNameContext(NameContext parentContext, List<NamedTypeReference> typeParams) {
    NameContextBuilder builder = NameContextBuilder.get().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.build();
  }

  @Override
  public String canonicalName() {
    return TypeFunctions.getCanonicalName(typeElement);
  }

  @Override
  public String simpleName() {
    return TypeFunctions.getSimpleName(typeElement);
  }

  @Override
  public String packageName() {
    return TypeFunctions.getPackageName(typeElement);
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    return typeParams;
  }

  @Override
  public List<CustomTypeReference> parentTypes() {
    return parentTypesGetter.execute();
  }

  @Override
  public List<AnnotationInstance> annotations() {
    return annotationsGetter.execute();
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
    return declaredMethodsGetter.execute();
  }

  @Override
  public List<MethodStatement> declaredMethodsWithName(String name) {
    return declaredMethods().stream()
        .filter(m -> name.equals(m.name()))
        .toList();
  }

  @Override
  public List<MethodStatement> actualMethods() {
    return actualMethodsGetter.execute();
  }

  @Override
  public List<MethodStatement> actualMethodsWithName(String name) {
    return actualMethods().stream()
        .filter(m -> name.equals(m.name()))
        .toList();
  }

  @Override
  public Collection<CustomType> dependencies() {
    return dependenciesGetter.execute();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.execute();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }
}
