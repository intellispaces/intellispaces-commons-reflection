package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatement;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

/**
 * The annotation instance.
 */
public interface AnnotationInstance extends Instance {

  @Override
  default Optional<AnnotationInstance> asAnnotation() {
    return Optional.of(this);
  }

  /**
   * Annotation type statement.
   */
  AnnotationStatement annotationStatement();

  /**
   * Annotation elements.
   */
  Collection<AnnotationElement> elements();

  Optional<Instance> elementValue(String elementName);

  <A extends Annotation> A asAnnotationOf(Class<A> aClass);
}
