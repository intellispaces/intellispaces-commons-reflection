package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatement;

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
  public AnnotationStatement annotationStatement() {
    throw new RuntimeException("Not implemented yet");
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
