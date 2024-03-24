package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public interface AnnotationStatementBuilder {

  static AnnotationStatement build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new AnnotationStatementAdapter(typeElement, nameContext, session);
  }
}
