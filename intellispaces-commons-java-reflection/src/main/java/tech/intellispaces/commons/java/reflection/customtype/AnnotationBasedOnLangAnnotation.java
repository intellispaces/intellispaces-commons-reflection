package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;

import java.lang.annotation.Annotation;

class AnnotationBasedOnLangAnnotation extends AbstractCustomTypeBasedLandClass implements AnnotationType {
  private final Class<? extends Annotation> annotationClass;

  AnnotationBasedOnLangAnnotation(Class<? extends Annotation> annotationClass) {
    super(annotationClass);
    this.annotationClass = annotationClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Annotation;
  }

  @Override
  public boolean isAbstract() {
    return false;
  }

  @Override
  public boolean isFinal() {
    return true;
  }
}
