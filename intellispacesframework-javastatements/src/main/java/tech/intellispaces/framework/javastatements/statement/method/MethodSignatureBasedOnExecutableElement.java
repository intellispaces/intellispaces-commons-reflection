package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContextBuilder;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.type.ExceptionCompatibleType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link ExecutableElement} to {@link MethodSignature}.
 */
class MethodSignatureBasedOnExecutableElement implements MethodSignature {
  private final ExecutableElement executableElement;
  private final List<NamedType> typeParams;
  private final Getter<Optional<Type>> returnTypeGetter;
  private final Getter<Optional<Instance>> defaultValueGetter;
  private final Getter<List<MethodParam>> paramsGetter;
  private final Getter<List<ExceptionCompatibleType>> exceptionsGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;

  MethodSignatureBasedOnExecutableElement(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    this.executableElement = executableElement;
    this.typeParams = TypeElementFunctions.getTypeParameters(executableElement, typeContext, session);
    TypeContext classNameContext = createNameContext(typeContext, this.typeParams);
    this.returnTypeGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodReturnType, executableElement, classNameContext, session);
    this.defaultValueGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodDefaultValueInstance, executableElement, session);
    this.paramsGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodParams, executableElement, classNameContext, session);
    this.exceptionsGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodExceptions, executableElement, classNameContext, session);
    this.annotationsGetter = Actions.cachedLazyGetter(TypeElementFunctions::getAnnotations, executableElement, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodSignature;
  }

  private TypeContext createNameContext(TypeContext parentContext, List<NamedType> typeParams) {
    TypeContextBuilder builder = TypeContexts.build().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.get();
  }

  @Override
  public String name() {
    return executableElement.getSimpleName().toString();
  }

  @Override
  public List<NamedType> typeParameters() {
    return typeParams;
  }

  @Override
  public Optional<Type> returnType() {
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
  public List<Type> parameterTypes() {
    return params().stream()
        .map(MethodParam::type)
        .toList();
  }

  @Override
  public List<ExceptionCompatibleType> exceptions() {
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
  public boolean isAbstract() {
    return executableElement.getModifiers().contains(Modifier.ABSTRACT);
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

  @Override
  public MethodSignature specify(Map<String, NotPrimitiveType> typeMapping) {
    return new MethodSignatureImpl(
        name(),
        isAbstract(),
        isPublic(),
        isDefault(),
        isStatic(),
        typeParameters(),
        returnType().map(t -> t.specify(typeMapping)).orElse(null),
        defaultValue().orElse(null),
        params().stream().map(p -> p.specify(typeMapping)).toList(),
        exceptions().stream().map(e -> (ExceptionCompatibleType) e.specify(typeMapping)).toList(),
        annotations()
    );
  }
}
