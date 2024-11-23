package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
