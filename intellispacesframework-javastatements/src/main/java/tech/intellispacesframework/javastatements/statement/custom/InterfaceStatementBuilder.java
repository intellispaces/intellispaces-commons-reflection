package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface InterfaceStatementBuilder {

  static InterfaceStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new InterfaceStatementAdapter(typeElement, typeContext, session);
  }
}
