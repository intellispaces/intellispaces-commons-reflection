package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

class InterfaceStatementAdapter extends CustomTypeStatementAdapter implements InterfaceStatement {

  InterfaceStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
