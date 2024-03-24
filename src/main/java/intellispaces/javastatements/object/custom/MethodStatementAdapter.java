package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.ElementFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.ExecutableElement;

class MethodStatementAdapter implements MethodStatement {
  private final CustomType holder;
  private final GetterAction<MethodSignature> signatureGetter;

  MethodStatementAdapter(ExecutableElement executableElement, CustomType holder, NameContext nameContext, Session session) {
    this.holder = holder;
    this.signatureGetter = ActionFunctions.cachedLazyGetter(ElementFunctions::asMethodSignature, executableElement, nameContext, session);
  }

  @Override
  public CustomType holder() {
    return holder;
  }

  @Override
  public MethodSignature signature() {
    return signatureGetter.execute();
  }
}
