package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface AnnotationStatementBuilder {

  static AnnotationStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new AnnotationStatementAdapter(typeElement, typeContext, session);
  }
}
