package intellispaces.javastatements.statement.custom;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;
import intellispaces.javastatements.statement.TypeElementFunctions;

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
