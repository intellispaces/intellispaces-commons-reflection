package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContextBuilder;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
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
  public MethodSignature specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
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
        exceptions().stream().map(e -> (ExceptionCompatibleTypeReference) e.specify(typeMapping)).toList(),
        annotations()
    );
  }
}
