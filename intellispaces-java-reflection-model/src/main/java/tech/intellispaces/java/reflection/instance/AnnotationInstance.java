package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.customtype.AnnotationType;

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
  AnnotationType annotationStatement();

  /**
   * Annotation elements.
   */
  Collection<AnnotationElement> elements();

  Optional<Instance> value();

  Optional<Instance> valueOf(String elementName);

  <A extends Annotation> A asAnnotationOf(Class<A> aClass);
}
