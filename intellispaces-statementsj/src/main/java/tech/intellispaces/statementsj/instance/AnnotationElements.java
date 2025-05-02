package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
