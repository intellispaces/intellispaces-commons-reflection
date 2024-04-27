package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface EnumStatementBuilder {

  static EnumStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new EnumStatementAdapter(typeElement, typeContext, session);
  }
}
