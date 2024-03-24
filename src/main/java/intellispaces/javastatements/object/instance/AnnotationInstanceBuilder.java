package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.AnnotationMirror;

public interface AnnotationInstanceBuilder {

  static AnnotationInstance build(AnnotationMirror annotationMirror, Session session) {
    return new AnnotationInstanceAdapter(annotationMirror, session);
  }
}
