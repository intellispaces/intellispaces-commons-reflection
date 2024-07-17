package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(Object value, String name, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
