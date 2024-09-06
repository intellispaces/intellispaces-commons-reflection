package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;

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
}
