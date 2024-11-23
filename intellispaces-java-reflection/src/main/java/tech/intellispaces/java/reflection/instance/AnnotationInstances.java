package tech.intellispaces.java.reflection.instance;

import java.lang.annotation.Annotation;

public interface AnnotationInstances {

  static AnnotationInstance of(Annotation annotation) {
    return new AnnotationInstanceBasedOnLangAnnotation(annotation);
  }
}
