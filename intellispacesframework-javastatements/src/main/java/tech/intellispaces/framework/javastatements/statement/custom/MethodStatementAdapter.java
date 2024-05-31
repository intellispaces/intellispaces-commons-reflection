package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

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
