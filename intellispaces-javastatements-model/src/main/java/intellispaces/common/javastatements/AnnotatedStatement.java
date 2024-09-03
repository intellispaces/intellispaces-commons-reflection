package intellispaces.common.javastatements;

import intellispaces.common.javastatements.instance.AnnotationInstance;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

/**
 * Statement to which the annotation attachment is applicable.
 */
public interface AnnotatedStatement extends Statement {

  /**
   * Attached annotations.
   */
  List<AnnotationInstance> annotations();

  Optional<AnnotationInstance> selectAnnotation(String annotationClass);

  <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass);

  boolean hasAnnotation(Class<? extends Annotation> annotationClass);
}
