package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface AnnotationStatementBuilder {

  static AnnotationStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new AnnotationStatementAdapter(typeElement, typeContext, session);
  }
}
