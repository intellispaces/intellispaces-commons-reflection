package intellispaces.javastatements.instance;

import intellispaces.javastatements.customtype.AnnotationType;

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

  Optional<Instance> elementValue(String elementName);

  <A extends Annotation> A asAnnotationOf(Class<A> aClass);
}
