package tech.intellispaces.javareflection.instance;

import tech.intellispaces.javareflection.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
