package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
