package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

class MethodParamAdapter implements MethodParam {
  private final VariableElement variableElement;
  private final Getter<TypeReference> typeGetter;
  private final Getter<List<AnnotationInstance>> annotationsGetter;

  MethodParamAdapter(VariableElement variableElement, TypeContext typeContext, Session session) {
    this.variableElement = variableElement;
    this.typeGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getTypeReference, variableElement.asType(), typeContext, session);
    this.annotationsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getAnnotations, variableElement, session);
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
}
