package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.ExecutableElement;

class MethodStatementAdapter implements MethodStatement {
  private final CustomType holder;
  private final Getter<MethodSignature> signatureGetter;

  MethodStatementAdapter(ExecutableElement executableElement, CustomType holder, TypeContext typeContext, Session session) {
    this.holder = holder;
    this.signatureGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::asMethodSignature, executableElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType holder() {
    return holder;
  }

  @Override
  public MethodSignature signature() {
    return signatureGetter.get();
  }
}
