package tech.intellispaces.commons.java.reflection.method;

import tech.intellispaces.commons.action.cache.CachedSupplierActions;
import tech.intellispaces.commons.action.supplier.SupplierAction;
import tech.intellispaces.commons.base.exception.NotImplementedExceptions;
import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.java.reflection.context.TypeContext;
import tech.intellispaces.commons.java.reflection.customtype.AnnotationFunctions;
import tech.intellispaces.commons.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;
import tech.intellispaces.commons.java.reflection.session.Session;

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
  private final SupplierAction<TypeReference> typeGetter;
  private final SupplierAction<List<AnnotationInstance>> annotationsGetter;

  MethodParamBasedOnVariableElement(VariableElement variableElement, TypeContext typeContext, Session session) {
    this.variableElement = variableElement;
    this.typeGetter = CachedSupplierActions.get(JavaModelFunctions::getTypeReference, variableElement.asType(), typeContext, session);
    this.annotationsGetter = CachedSupplierActions.get(JavaModelFunctions::getAnnotations, variableElement, session);
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
  public MethodParam effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodParamImpl(
        name(),
        type().effective(typeMapping),
        annotations()
    );
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("L1H8lQ");
  }
}
