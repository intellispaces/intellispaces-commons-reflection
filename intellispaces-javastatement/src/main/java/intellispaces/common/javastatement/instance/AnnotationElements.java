package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.session.Session;

public interface AnnotationElements {

  static AnnotationElement of(String name, Object value, Session session) {
    return new AnnotationElementBasedOnObject(name, value, session);
  }
}
