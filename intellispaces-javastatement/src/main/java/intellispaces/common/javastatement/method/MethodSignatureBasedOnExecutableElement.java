package intellispaces.common.javastatement.method;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.base.exception.NotImplementedException;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.context.TypeContextBuilder;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.customtype.AnnotationFunctions;
import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.instance.Instance;
import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.reference.ThrowableReference;
import intellispaces.common.javastatement.reference.TypeReference;
import intellispaces.common.javastatement.session.Session;

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
  private final Getter<List<ThrowableReference>> exceptionsGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;

  MethodSignatureBasedOnExecutableElement(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    this.executableElement = executableElement;
    this.typeParams = JavaModelFunctions.getTypeParameters(executableElement, typeContext, session);
    TypeContext classNameContext = createNameContext(typeContext, this.typeParams);
    this.returnTypeGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodReturnType, executableElement, classNameContext, session);
    this.defaultValueGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodDefaultValueInstance, executableElement, session);
    this.paramsGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodParams, executableElement, classNameContext, session);
    this.exceptionsGetter = Actions.cachedLazyGetter(MethodFunctions::getMethodExceptions, executableElement, classNameContext, session);
    this.annotationsGetter = Actions.cachedLazyGetter(JavaModelFunctions::getAnnotations, executableElement, session);
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
  public List<ThrowableReference> exceptions() {
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
  public MethodSignature effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodSignatureImpl(
        name(),
        isAbstract(),
        isPublic(),
        isDefault(),
        isStatic(),
        typeParameters(),
        returnType().map(t -> t.effective(typeMapping)).orElse(null),
        defaultValue().orElse(null),
        params().stream().map(p -> p.effective(typeMapping)).toList(),
        exceptions().stream().map(e -> (ThrowableReference) e.effective(typeMapping)).toList(),
        annotations()
    );
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedException.withCode("L/bV+w");
  }
}
