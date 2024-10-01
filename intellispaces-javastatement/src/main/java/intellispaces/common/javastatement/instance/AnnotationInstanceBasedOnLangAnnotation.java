package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.customtype.AnnotationType;
import intellispaces.common.javastatement.customtype.Annotations;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

/**
 * Adapter of {@link Annotation} to {@link AnnotationInstance}.
 */
class AnnotationInstanceBasedOnLangAnnotation implements AnnotationInstance {
  private final Annotation annotation;

  AnnotationInstanceBasedOnLangAnnotation(Annotation annotation) {
    this.annotation = annotation;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  public AnnotationType annotationStatement() {
    return Annotations.of(annotation.annotationType());
  }

  @Override
  public Collection<AnnotationElement> elements() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public Optional<Instance> elementValue(String elementName) {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> aClass) {
    throw new RuntimeException("Not implemented yet");
  }
}
