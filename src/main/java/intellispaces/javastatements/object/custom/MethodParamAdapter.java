package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.AnnotationFunctions;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

class MethodParamAdapter implements MethodParam {
  private final VariableElement variableElement;
  private final GetterAction<TypeReference> typeGetter;
  private final GetterAction<List<AnnotationInstance>> annotationsGetter;

  MethodParamAdapter(VariableElement variableElement, NameContext nameContext, Session session) {
    this.variableElement = variableElement;
    this.typeGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getTypeReference, variableElement.asType(), nameContext, session);
    this.annotationsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getAnnotations, variableElement, session);
  }

  @Override
  public String name() {
    return variableElement.getSimpleName().toString();
  }

  @Override
  public TypeReference type() {
    return typeGetter.execute();
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
}
