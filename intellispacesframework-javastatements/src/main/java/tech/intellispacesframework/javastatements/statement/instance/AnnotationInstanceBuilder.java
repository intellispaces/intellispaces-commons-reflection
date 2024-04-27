package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.AnnotationMirror;

public interface AnnotationInstanceBuilder {

  static AnnotationInstance build(AnnotationMirror annotationMirror, Session session) {
    return new AnnotationInstanceAdapter(annotationMirror, session);
  }
}
