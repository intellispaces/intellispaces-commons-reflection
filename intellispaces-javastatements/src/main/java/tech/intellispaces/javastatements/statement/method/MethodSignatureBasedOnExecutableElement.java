package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContextBuilder;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.javastatements.statement.instance.Instance;
import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.statement.reference.ThrowableTypeReference;
import tech.intellispaces.javastatements.statement.reference.TypeReference;
import tech.intellispaces.javastatements.statement.customtype.AnnotationFunctions;

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
  private final List<NamedReference> typeParams;
  private final Getter<Optional<TypeReference>> returnTypeGetter;
  private final Getter<Optional<Instance>> defaultValueGetter;
  private final Getter<List<MethodParam>> paramsGetter;
  private final Getter<List<ThrowableTypeReference>> exceptionsGetter;
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

  private TypeContext createNameContext(TypeContext parentContext, List<NamedReference> typeParams) {
    TypeContextBuilder builder = TypeContexts.build().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.get();
  }

  @Override
  public String name() {
    return executableElement.getSimpleName().toString();
  }

  @Override
  public List<NamedReference> typeParameters() {
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
  public List<TypeReference> parameterTypes() {
    return params().stream()
        .map(MethodParam::type)
        .toList();
  }

  @Override
  public List<ThrowableTypeReference> exceptions() {
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
  public MethodSignature specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
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
        exceptions().stream().map(e -> (ThrowableTypeReference) e.specify(typeMapping)).toList(),
        annotations()
    );
  }
}
