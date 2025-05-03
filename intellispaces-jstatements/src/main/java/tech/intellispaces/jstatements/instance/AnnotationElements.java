package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
