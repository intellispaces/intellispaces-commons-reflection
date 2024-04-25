package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.session.Session;

public interface AnnotationElementBuilder {

  static AnnotationElement build(String name, Object value, Session session) {
    return new AnnotationElementAdapter(name, value, session);
  }
}
