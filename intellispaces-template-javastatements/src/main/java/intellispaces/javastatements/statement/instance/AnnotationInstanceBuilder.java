package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.session.Session;

import javax.lang.model.element.AnnotationMirror;

public interface AnnotationInstanceBuilder {

  static AnnotationInstance build(AnnotationMirror annotationMirror, Session session) {
    return new AnnotationInstanceAdapter(annotationMirror, session);
  }
}
