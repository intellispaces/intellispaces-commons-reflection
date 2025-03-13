package tech.intellispaces.commons.reflection.instance;

import tech.intellispaces.commons.reflection.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
