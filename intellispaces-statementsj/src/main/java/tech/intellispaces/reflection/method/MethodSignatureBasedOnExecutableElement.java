package tech.intellispaces.statementsj.method;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.common.JavaModelFunctions;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.context.TypeContextBuilder;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.customtype.AnnotationFunctions;
import tech.intellispaces.statementsj.instance.AnnotationInstance;
import tech.intellispaces.statementsj.instance.Instance;
import tech.intellispaces.statementsj.reference.NamedReference;
import tech.intellispaces.statementsj.reference.NotPrimitiveReference;
import tech.intellispaces.statementsj.reference.ThrowableReference;
import tech.intellispaces.statementsj.reference.TypeReference;
import tech.intellispaces.statementsj.session.Session;

/**
 * Adapter of {@link ExecutableElement} to {@link MethodSignature}.
 */
class MethodSignatureBasedOnExecutableElement implements MethodSignature {
  private final ExecutableElement executableElement;
  private final List<NamedReference> typeParams;
  private final SupplierAction<Optional<TypeReference>> returnTypeGetter;
  private final SupplierAction<Optional<Instance>> defaultValueGetter;
  private final SupplierAction<List<MethodParam>> paramsGetter;
  private final SupplierAction<List<ThrowableReference>> exceptionsGetter;
  private final SupplierAction<List<AnnotationInstance>> annotationsGetter;

  MethodSignatureBasedOnExecutableElement(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    this.executableElement = executableElement;
    this.typeParams = JavaModelFunctions.getTypeParameters(executableElement, typeContext, session);
    TypeContext classNameContext = createNameContext(typeContext, this.typeParams);
    this.returnTypeGetter = CachedSupplierActions.get(MethodFunctions::getMethodReturnType, executableElement, classNameContext, session);
    this.defaultValueGetter = CachedSupplierActions.get(MethodFunctions::getMethodDefaultValueInstance, executableElement, session);
    this.paramsGetter = CachedSupplierActions.get(MethodFunctions::getMethodParams, executableElement, classNameContext, session);
    this.exceptionsGetter = CachedSupplierActions.get(MethodFunctions::getMethodExceptions, executableElement, classNameContext, session);
    this.annotationsGetter = CachedSupplierActions.get(JavaModelFunctions::getAnnotations, executableElement, session);
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
    throw NotImplementedExceptions.withCode("L/bV+w");
  }
}
