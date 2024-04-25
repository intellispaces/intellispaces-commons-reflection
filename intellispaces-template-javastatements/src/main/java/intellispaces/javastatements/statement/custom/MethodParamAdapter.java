package intellispaces.javastatements.statement.custom;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.statement.instance.AnnotationInstance;
import intellispaces.javastatements.statement.reference.TypeReference;
import intellispaces.javastatements.session.Session;

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
