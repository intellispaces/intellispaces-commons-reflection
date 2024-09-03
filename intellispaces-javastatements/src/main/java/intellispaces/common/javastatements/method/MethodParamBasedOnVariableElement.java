package intellispaces.common.javastatements.method;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.customtype.AnnotationFunctions;
import intellispaces.common.javastatements.instance.AnnotationInstance;
import intellispaces.common.javastatements.reference.NotPrimitiveReference;
import intellispaces.common.javastatements.reference.TypeReference;
import intellispaces.common.javastatements.session.Session;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link VariableElement} to {@link MethodParam}.
 */
class MethodParamBasedOnVariableElement implements MethodParam {
  private final VariableElement variableElement;
  private final Getter<TypeReference> typeGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;

  MethodParamBasedOnVariableElement(VariableElement variableElement, TypeContext typeContext, Session session) {
    this.variableElement = variableElement;
    this.typeGetter = Actions.cachedLazyGetter(JavaModelFunctions::getTypeReference, variableElement.asType(), typeContext, session);
    this.annotationsGetter = Actions.cachedLazyGetter(JavaModelFunctions::getAnnotations, variableElement, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodParam;
  }

  @Override
  public String name() {
    return variableElement.getSimpleName().toString();
  }

  @Override
  public TypeReference type() {
    return typeGetter.get();
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
  public MethodParam specify(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodParamImpl(
        name(),
        type().specify(typeMapping),
        annotations()
    );
  }
}
