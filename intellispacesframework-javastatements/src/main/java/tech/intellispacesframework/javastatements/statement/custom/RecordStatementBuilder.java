package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface RecordStatementBuilder {

  static RecordStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new RecordStatementAdapter(typeElement, typeContext, session);
  }
}
