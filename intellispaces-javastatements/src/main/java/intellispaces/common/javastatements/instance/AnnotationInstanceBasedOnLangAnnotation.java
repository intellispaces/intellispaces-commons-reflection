package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.customtype.Annotations;
import intellispaces.common.javastatements.customtype.AnnotationType;

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
