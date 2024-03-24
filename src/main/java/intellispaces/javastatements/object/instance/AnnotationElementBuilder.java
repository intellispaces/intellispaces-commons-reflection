package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.AnnotationElement;
import intellispaces.javastatements.model.session.Session;

public interface AnnotationElementBuilder {

  static AnnotationElement build(String name, Object value, Session session) {
    return new AnnotationElementAdapter(name, value, session);
  }
}
