package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
