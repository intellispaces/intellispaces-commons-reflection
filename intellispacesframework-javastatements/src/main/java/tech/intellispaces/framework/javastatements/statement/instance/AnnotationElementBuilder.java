package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.session.Session;

public interface AnnotationElementBuilder {

  static AnnotationElement build(String name, Object value, Session session) {
    return new AnnotationElementAdapter(name, value, session);
  }
}
