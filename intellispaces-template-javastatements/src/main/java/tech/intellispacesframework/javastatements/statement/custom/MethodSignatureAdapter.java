package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.statement.method.MethodFunctions;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.context.TypeContextBuilder;
import tech.intellispacesframework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispacesframework.javastatements.statement.instance.Instance;
import tech.intellispacesframework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

class MethodSignatureAdapter implements MethodSignature {
  private final ExecutableElement executableElement;
  private final List<NamedTypeReference> typeParams;
  private final Getter<Optional<TypeReference>> returnTypeGetter;
  private final Getter<Optional<Instance>> defaultValueGetter;
  private final Getter<List<MethodParam>> paramsGetter;
  private final Getter<List<ExceptionCompatibleTypeReference>> exceptionsGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;

  MethodSignatureAdapter(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    this.executableElement = executableElement;
    this.typeParams = TypeElementFunctions.getTypeParameters(executableElement, typeContext, session);
    TypeContext classNameContext = createNameContext(typeContext, this.typeParams);
    this.returnTypeGetter = ActionBuilders.cachedLazyGetter(MethodFunctions::getMethodReturnType, executableElement, classNameContext, session);
    this.defaultValueGetter = ActionBuilders.cachedLazyGetter(MethodFunctions::getMethodDefaultValueInstance, executableElement, session);
    this.paramsGetter = ActionBuilders.cachedLazyGetter(MethodFunctions::getMethodParams, executableElement, classNameContext, session);
    this.exceptionsGetter = ActionBuilders.cachedLazyGetter(MethodFunctions::getMethodExceptions, executableElement, classNameContext, session);
    this.annotationsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getAnnotations, executableElement, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodSignature;
  }

  private TypeContext createNameContext(TypeContext parentContext, List<NamedTypeReference> typeParams) {
    TypeContextBuilder builder = TypeContextBuilder.get().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.build();
  }

  @Override
  public String name() {
    return executableElement.getSimpleName().toString();
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    return typeParams;
  }

  @Override
  public Optional<TypeReference> returnType() {
    return returnTypeGetter.get();
  }

  @Override
  public Optional<Instance> defaultValue() {
    return defaultValueGetter.get();
  }

  @Override
  public List<MethodParam> params() {
    return paramsGetter.get();
  }

  @Override
  public List<ExceptionCompatibleTypeReference> exceptions() {
    return exceptionsGetter.get();
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
  public boolean isPublic() {
    return executableElement.getModifiers().contains(Modifier.PUBLIC);
  }

  @Override
  public boolean isDefault() {
    return executableElement.isDefault();
  }

  @Override
  public boolean isStatic() {
    return executableElement.getModifiers().contains(Modifier.STATIC);
  }
}
