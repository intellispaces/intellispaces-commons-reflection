package tech.intellispaces.commons.java.reflection.instance;

import tech.intellispaces.commons.java.reflection.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
