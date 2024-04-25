package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface AnnotationStatementBuilder {

  static AnnotationStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new AnnotationStatementAdapter(typeElement, typeContext, session);
  }
}
